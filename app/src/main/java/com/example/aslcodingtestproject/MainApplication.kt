package com.example.aslcodingtestproject


import android.app.Application
import android.os.Build
import com.example.aslcodingtestproject.constant.util.DpiHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

// Application for Dagger Hilt
@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        private var instance: MainApplication? = null
        fun get(): MainApplication? {
            return instance
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        printDPI()

        // Print SDK Level
        Timber.i("SDK level: ${Build.VERSION.SDK_INT}")

    }

    private fun printDPI() {
        if (BuildConfig.DEBUG) {
            val strDpi = DpiHelper.getDeviceDensityString(this)
            Timber.i("printDPI(), dpi: $strDpi")
        }
    }
}