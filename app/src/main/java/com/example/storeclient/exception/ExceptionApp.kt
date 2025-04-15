package com.example.storeclient.exception
import android.app.Application
import android.util.Log


class ExceptionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler)

    }
}