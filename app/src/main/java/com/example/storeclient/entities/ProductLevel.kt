package com.example.storeclient.entities

enum class ProductLevel(val thresold: Int){
    LOW(25),
    MEDIUM(100),
    HIGH(500)
}