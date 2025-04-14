package com.example.storeclient.crypto

import android.util.Log
import retrofit2.Converter
import retrofit2.Retrofit
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import java.lang.reflect.Type

class DecryptingConverterFactory(
    private val baseFactory: Converter.Factory
) : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val gsonConverter = baseFactory.responseBodyConverter(type, annotations, retrofit)

        return Converter<ResponseBody, Any> { encryptedBody ->
            val encryptedText = encryptedBody.string().trim()

            Log.d("Decrypting", "🔐 Encrypted text received:\n$encryptedText")

            val decryptedJson = try {
                // Si empieza como JSON válido, no desencriptamos
                if (encryptedText.startsWith("{") || encryptedText.startsWith("[")) {
                    Log.d("Decrypting", "🟢 Plain JSON detected, skipping decryption")
                    encryptedText
                } else {
                    Log.d("Decrypting", "🔐 Encrypted Base64 detected, proceeding to decrypt")
                    CryptoHelper.decrypt(encryptedText)
                }
            } catch (e: Exception) {
                Log.e("Decrypting", "❌ Error decrypting body: ${e.message}")
                throw e
            }

            Log.d("Decrypting", "✅ Decrypted JSON:\n$decryptedJson")

            val cleanBody = decryptedJson.toResponseBody("application/json".toMediaType())
            gsonConverter!!.convert(cleanBody)
        }
    }
}
