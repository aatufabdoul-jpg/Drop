package com.drop.filetransfer.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WifiDirectService : Service() {

    private lateinit var wifiP2pManager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var broadcastReceiver: BroadcastReceiver
    private val discoveredDevices = mutableListOf<WifiP2pDevice>()

    private val TAG = "WifiDirectService"

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "WiFi Direct Service Created")
        
        wifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = wifiP2pManager.initialize(this, mainLooper, null)
        
        registerBroadcastReceiver()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "WiFi Direct Service Started")
        startDiscovery()
        return START_STICKY
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        }
        
        broadcastReceiver = WifiP2pBroadcastReceiver(wifiP2pManager, channel, this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    private fun startDiscovery() {
        Log.d(TAG, "Starting WiFi Direct Discovery")
        wifiP2pManager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.d(TAG, "Discovery started successfully")
            }

            override fun onFailure(reasonCode: Int) {
                Log.e(TAG, "Discovery failed: $reasonCode")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        Log.d(TAG, "WiFi Direct Service Destroyed")
    }
}

class WifiP2pBroadcastReceiver(
    private val wifiP2pManager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val service: WifiDirectService
) : BroadcastReceiver() {

    private val TAG = "WifiP2pBroadcast"

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            when (it.action) {
                WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                    val state = it.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                    handleStateChanged(state)
                }
                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    val peerList = it.getParcelableExtra<WifiP2pDeviceList>(WifiP2pManager.EXTRA_P2P_DEVICE_LIST)
                    handlePeersChanged(peerList)
                }
                WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                    Log.d(TAG, "Connection changed")
                }
                WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                    val device = it.getParcelableExtra<WifiP2pDevice>(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE)
                    Log.d(TAG, "This device: ${device?.deviceName}")
                }
            }
        }
    }

    private fun handleStateChanged(state: Int) {
        Log.d(TAG, "WiFi P2P State: $state")
    }

    private fun handlePeersChanged(peerList: WifiP2pDeviceList?) {
        Log.d(TAG, "Peers changed: ${peerList?.deviceList?.size} device(s) found")
        peerList?.deviceList?.forEach { device ->
            Log.d(TAG, "Device: ${device.deviceName} - ${device.deviceAddress}")
        }
    }
}
