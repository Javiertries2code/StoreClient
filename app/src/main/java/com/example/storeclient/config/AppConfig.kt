package com.example.storeclient.config

object AppConfig {

    const val IMAGE_COMPRESSION_QUALITY = 70

    const val AUTO_LOGOUT_DELAY_MS = 10 * 60 * 1000L // 10 minutos
    const val AUTO_LOGOUT_DEBUG = 200000L

    const val DELAY_NO_ACTION = 100000L

    const val DEV_SKIP_LOGIN = false

    const val DEV_ENCRYTING_PASSWROD = "k0ZH5SszCQEx0pJm9WiG6ER5rhgDuBBJw4sk+IzqZx4="



    //Email SMTP
    const val SMTP_HOST =  "smtp.gmail.com"
    const val SMTP_PORT = "587"
    const val EMAIL_USER = "javier.bravogu@elorrieta-errekamari.com"
    const val EMAIL_PASSWORD = "2025@Elorrieta"
    const val RECEIVER_EMAIL = "javier.bravogu@elorrieta-errekamari.com"

    // Cifrado
    const val ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding"
    const val ENCRYPTION_KEY_LENGTH = 256 // bits
    const val ENCRYPTION_IV_LENGTH = 16 // bytes (128 bits)


    const val DEBUG_MODE = false
}