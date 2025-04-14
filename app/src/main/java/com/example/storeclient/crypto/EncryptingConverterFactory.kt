package com.example.storeclient.crypto

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class EncryptingConverterFactory(
    private val baseFactory: Converter.Factory
) : Converter.Factory() {

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<Any, RequestBody>? {
        val gsonConverter = baseFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        ) as? Converter<Any, RequestBody>

        return Converter { value ->
            val json = gsonConverter?.convert(value)?.let { body ->
                bodyToString(body)
            }?.trim()

            val encrypted = if (!json.isNullOrBlank() && (json.startsWith("{") || json.startsWith("["))) {
                Log.d("Encrypting", "Encrypting JSON:\n$json")
                CryptoHelper.encrypt(json)
            } else {
                Log.d("Encrypting", "Already encrypted or invalid JSON:\n$json")
                json ?: ""
            }

            encrypted.toRequestBody("text/plain".toMediaType())
        }
    }

    private fun bodyToString(body: RequestBody): String {
        return try {
            val buffer = okio.Buffer()
            body.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: Exception) {
            Log.e("EncryptingConverter", "Error converting body to string", e)
            ""
        }
    }
}
