package com.example.storeclient.config

object AppConfig {

    const val IMAGE_COMPRESSION_QUALITY = 70

    // Cifrado (lo usaremos más adelante)
    const val ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding"
    const val ENCRYPTION_KEY_LENGTH = 256 // bits
    const val ENCRYPTION_IV_LENGTH = 16 // bytes (128 bits)


    const val DEBUG_MODE = true
}