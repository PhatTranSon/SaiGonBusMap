package com.example.busmap

import android.app.Application
import com.google.android.libraries.places.api.Places

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialization
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.map_api_key))
        }
    }
}