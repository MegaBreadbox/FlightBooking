package com.example.flightbooking

import android.app.Application
import com.example.flightbooking.data.AppContainer
import com.example.flightbooking.data.DefaultAppContainer

class FlightApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}