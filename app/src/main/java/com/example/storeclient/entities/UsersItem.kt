package com.example.storeclient.entities

data class UsersItem(
    val email: String,
    val enabled: Int,
    val image: Any,
    val name: String,
    val pass: String,
    val rol: String,
    val userId: Int
)