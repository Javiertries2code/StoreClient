package com.example.storeclient.model

data class ApiResponse<T>(
    val success: Boolean,
    val status: String,
    val message: String,
    val data: T,
    val type: String? = null
)
