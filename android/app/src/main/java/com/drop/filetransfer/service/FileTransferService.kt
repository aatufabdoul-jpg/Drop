package com.drop.filetransfer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

class FileTransferService : Service() {

    private val TAG = "FileTransferService"
    private val binder = LocalBinder()
    private var transferJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())

    inner class LocalBinder : Binder() {
        fun getService(): FileTransferService = this@FileTransferService
    }

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "File Transfer Service Started")
        return START_STICKY
    }

    fun sendFile(filePath: String, receiverIp: String, receiverPort: Int) {
        transferJob = serviceScope.launch {
            try {
                Log.d(TAG, "Starting file transfer: $filePath to $receiverIp:$receiverPort")
                val file = File(filePath)
                val socket = Socket(receiverIp, receiverPort)
                val outputStream = socket.getOutputStream()
                
                val fileInputStream = FileInputStream(file)
                val buffer = ByteArray(8192)
                var bytesRead: Int
                
                while (fileInputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                
                fileInputStream.close()
                outputStream.close()
                socket.close()
                
                Log.d(TAG, "File transfer completed")
            } catch (e: IOException) {
                Log.e(TAG, "Error sending file: ${e.message}")
            }
        }
    }

    fun receiveFile(port: Int, savePath: String) {
        transferJob = serviceScope.launch {
            try {
                Log.d(TAG, "Starting file reception on port $port")
                val serverSocket = ServerSocket(port)
                val socket = serverSocket.accept()
                val inputStream = socket.getInputStream()
                
                val file = File(savePath)
                val fileOutputStream = FileOutputStream(file)
                val buffer = ByteArray(8192)
                var bytesRead: Int
                
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead)
                }
                
                fileOutputStream.close()
                inputStream.close()
                socket.close()
                serverSocket.close()
                
                Log.d(TAG, "File reception completed: $savePath")
            } catch (e: IOException) {
                Log.e(TAG, "Error receiving file: ${e.message}")
            }
        }
    }

    fun cancelTransfer() {
        transferJob?.cancel()
        Log.d(TAG, "Transfer cancelled")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Log.d(TAG, "File Transfer Service Destroyed")
    }
}
