package com.example.testproject

import android.app.Application
import android.content.Context

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}