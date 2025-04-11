package com.example.storeclient.utils


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.storeclient.config.AppConfig
import java.io.ByteArrayOutputStream

fun Bitmap.toBase64String(): String {
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, AppConfig.IMAGE_COMPRESSION_QUALITY
        , outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun String.toBitmap(): Bitmap {
    val decodedBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
