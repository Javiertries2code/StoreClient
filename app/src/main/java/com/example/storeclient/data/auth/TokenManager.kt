package com.example.storeclient.data.auth

import android.content.Context
import android.content.SharedPreferences

/**
 * it handles the recived tokenn and ecnrypting password, in a further implementacion,
 * it should use encrypted preferences
 */
object TokenManager {

    private const val PREFS_NAME = "auth_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val CRYPTO_KEY = "crypto_key"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveCryptoKey(cryptoKey: String?) {
        prefs.edit().apply {
            putString(CRYPTO_KEY, cryptoKey)
            apply()
        }

    }


    fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit().apply {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS_TOKEN, null)

    fun getRefreshToken(): String? = prefs.getString(KEY_REFRESH_TOKEN, null)

    fun getCryptoKey(): String? = prefs.getString(CRYPTO_KEY, null)

    fun clearTokens() {
        //i make sure in here i dont remove the password
        prefs.edit().apply {
            remove(KEY_ACCESS_TOKEN)
            remove(KEY_REFRESH_TOKEN)
            apply()
        }
    }
}
