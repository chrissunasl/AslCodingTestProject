package com.example.aslcodingtestproject


import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant


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
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

    }


}