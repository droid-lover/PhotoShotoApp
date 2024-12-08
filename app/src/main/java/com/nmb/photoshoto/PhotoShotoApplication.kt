package com.nmb.photoshoto

import android.app.Application
import com.nmb.utilities.logging.AppLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PhotoShotoApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        AppLogger.d(message = "Application is launched")
    }

    companion object {
        const val TAG = "PhotoShotoApplication"
    }
}