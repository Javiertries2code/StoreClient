package com.example.storeclient.entities

data class ProductsItem(
     val amount: Int,
    val cost: Double,
    val enabled: Int,
    val image: Any? = null,
    val minimumAmount: Int,
    val name: String,
    val productId: Int? = null,
    val retailPrice: Double,
    val season: Int
)