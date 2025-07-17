package com.example.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TastifyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        deleteDatabase("peya-database")
    }
}