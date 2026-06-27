package com.drop.filetransfer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DropApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Application initialization
    }
}
