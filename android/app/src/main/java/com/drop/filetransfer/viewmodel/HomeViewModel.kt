package com.drop.filetransfer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _wifiDirectState = MutableLiveData<String>("Initialisation...")
    val wifiDirectState: LiveData<String> get() = _wifiDirectState

    private val _discoveredDevices = MutableLiveData<List<String>>(emptyList())
    val discoveredDevices: LiveData<List<String>> get() = _discoveredDevices

    fun onSendFileClicked() {
        // Navigate to send fragment
    }

    fun onReceiveFileClicked() {
        // Navigate to receive fragment
    }

    fun updateWifiState(state: String) {
        _wifiDirectState.value = state
    }

    fun updateDevices(devices: List<String>) {
        _discoveredDevices.value = devices
    }
}
