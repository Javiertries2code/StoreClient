package com.example.storeclient.data.interceptors

import com.example.storeclient.helpers.CryptoHelper
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer

class EncryptionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Only encrypt POST and PUT requests with a body
        if (originalRequest.method != "POST" && originalRequest.method != "PUT") {
            return chain.proceed(originalRequest)
        }

        val originalBody = originalRequest.body ?: return chain.proceed(originalRequest)

        // Read the original body as string
        val buffer = Buffer()
        originalBody.writeTo(buffer)
        val originalContent = buffer.readUtf8()

        // Encrypt the content
        val encryptedBase64 = CryptoHelper.encrypt(originalContent)

        // Create new encrypted request body
        val encryptedRequestBody = encryptedBase64.toRequestBody("text/plain".toMediaType())

        // Build the new request with encrypted body
        val newRequest = originalRequest.newBuilder()
            .method(originalRequest.method, encryptedRequestBody)
            .build()

        return chain.proceed(newRequest)
    }
}
