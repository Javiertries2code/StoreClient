package com.example.storeclient.entities

data class ProductsItem(
    val amount: Int,
    val cost: Double,
    val enabled: Int,
    val image: Any,
    val minimumAmount: Int,
    val name: String,
    val productId: Int,
    val retailPrice: Double,
    val season: Int
)