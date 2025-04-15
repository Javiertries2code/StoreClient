package com.example.storeclient.exception

import android.util.Log

// core/GlobalExceptionHandler.kt
object GlobalExceptionHandler : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e("GLOBAL_EXCEPTION", "Crash in ${t.name}: ${e.message}", e)
        // Aquí puedes guardar en archivo, notificar a Firebase, etc.
    }
}
