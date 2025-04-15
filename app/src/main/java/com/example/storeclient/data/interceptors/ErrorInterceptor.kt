package com.example.storeclient.data.interceptors

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException

class ErrorInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val body = response.body
        val content = body?.string() ?: ""

        try {
            val json = JSONObject(content)

            if (json.has("success") && !json.getBoolean("success")) {
                val errorMessage = json.optString("message", "Unknown error")
                val errorCode = json.optString("status", "Unknown status")
                Log.d("errorCode","$errorCode" )

                // Mostrar Toast en el hilo principal
                Handler(Looper.getMainLooper()).post {
                    Log.d("errorCode","error interceptor --$errorCode" )
                    Toast.makeText(
                        context,
                        "[$errorCode] $errorMessage",
                        Toast.LENGTH_LONG
                    ).show()
                }

                throw ApiException("Error [$errorCode]: $errorMessage")
            }

            // reconstruir el cuerpo porque fue leído
            val newBody = ResponseBody.create(body?.contentType(), content)
            return response.newBuilder().body(newBody).build()

        } catch (e: Exception) {
            throw IOException("Invalid response from server", e)
        }
    }
}

class ApiException(message: String) : IOException(message)
