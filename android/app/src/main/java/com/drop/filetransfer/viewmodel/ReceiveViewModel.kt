package com.drop.filetransfer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReceiveViewModel @Inject constructor() : ViewModel() {

    private val _incomingTransfers = MutableLiveData<List<String>>(emptyList())
    val incomingTransfers: LiveData<List<String>> get() = _incomingTransfers

    private val _listeningStatus = MutableLiveData<Boolean>(false)
    val listeningStatus: LiveData<Boolean> get() = _listeningStatus

    fun startListening() {
        _listeningStatus.value = true
        // Start listening for incoming transfers
    }

    fun stopListening() {
        _listeningStatus.value = false
        // Stop listening
    }

    fun updateIncomingTransfers(transfers: List<String>) {
        _incomingTransfers.value = transfers
    }
}
