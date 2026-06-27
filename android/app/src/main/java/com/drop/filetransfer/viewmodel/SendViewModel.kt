package com.drop.filetransfer.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendViewModel @Inject constructor() : ViewModel() {

    private val _selectedFiles = MutableLiveData<List<Uri>>(emptyList())
    val selectedFiles: LiveData<List<Uri>> get() = _selectedFiles

    private val _transferProgress = MutableLiveData<Int>(0)
    val transferProgress: LiveData<Int> get() = _transferProgress

    fun onFilesSelected(uris: List<Uri>) {
        _selectedFiles.value = uris
    }

    fun startTransfer() {
        // Implement file transfer logic
        _transferProgress.value = 0
    }

    fun updateProgress(progress: Int) {
        _transferProgress.value = progress
    }
}
