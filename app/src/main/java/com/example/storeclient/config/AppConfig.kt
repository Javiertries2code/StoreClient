package com.example.storeclient.config

object AppConfig {

    const val IMAGE_COMPRESSION_QUALITY = 70

    const val AUTO_LOGOUT_DELAY_MS = 10 * 60 * 1000L // 10 minutos
    const val AUTO_LOGOUT_DEBUG = 20 * 60 * 2000L


    // Cifrado (lo usaremos más adelante)
    const val ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding"
    const val ENCRYPTION_KEY_LENGTH = 256 // bits
    const val ENCRYPTION_IV_LENGTH = 16 // bytes (128 bits)


    const val DEBUG_MODE = true
}