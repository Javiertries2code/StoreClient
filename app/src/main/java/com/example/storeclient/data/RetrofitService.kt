package com.example.storeclient.data

import androidx.compose.ui.graphics.vector.Path
import com.example.storeclient.entities.Products
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.entities.Users
import com.example.storeclient.entities.UsersItem

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {

    @GET("products/{id}")
    suspend fun getOneProduct(@Path("id") id: String): ProductsItem

    @GET("products")
    suspend fun getAllProducts(): Products


    @GET("users/{id}")
    suspend fun getOneUser(@Path("id") id: String): UsersItem

    @GET("users")
    suspend fun getAllUsers(): Users

    @POST("products/minus/{id}")
    suspend fun deleteProduct(@Path("id") productId: Int)

    @POST("products/plus/{id}")
    suspend fun addProduct(@Path("id") productId: Int)
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
