package com.example.storeclient.helpers

import android.util.Base64
import android.util.Log
import com.example.storeclient.config.AppConfig
import com.example.storeclient.data.auth.TokenManager
import com.google.crypto.tink.subtle.AesGcmJce

object CryptoHelper {

  private val base64Key: String by lazy {
        TokenManager.getCryptoKey()
            ?: throw IllegalStateException("Crypto key not found")
    }
//    val base64Key = "MTIzNDU2Nzg5MDEyMzQ1Ng=="
//val base64Key = AppConfig.DEV_ENCRYTING_PASSWROD

    private val aead by lazy {
        Log.d("key_retrieved", base64Key)

        val rawKey = Base64.decode(base64Key, Base64.NO_WRAP)
        AesGcmJce(rawKey) // ← aquí usamos directamente la clase simple
    }

    fun encrypt(plainText: String): String {
        val encrypted = aead.encrypt(plainText.toByteArray(), null)
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    fun decrypt(cipherTextBase64: String): String {
        val cipherBytes = Base64.decode(cipherTextBase64, Base64.NO_WRAP)
        val decrypted = aead.decrypt(cipherBytes, null)
        return String(decrypted)
    }
}
