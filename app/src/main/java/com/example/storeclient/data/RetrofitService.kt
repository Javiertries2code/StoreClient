package com.example.storeclient.data

import androidx.compose.ui.graphics.vector.Path
import com.example.storeclient.entities.Products
import com.example.storeclient.entities.ProductsItem

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("products/{id}")
    suspend fun getOneProduct(@Path("id") id: String): ProductsItem

    @GET("products")
    suspend fun getAllProducts(): Products
}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8099/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}
