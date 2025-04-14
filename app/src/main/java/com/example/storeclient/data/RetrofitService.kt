package com.example.storeclient.data

import com.example.storeclient.entities.Products
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.entities.Users
import com.example.storeclient.entities.UsersItem
import com.example.storeclient.model.CryptoKeys
import com.example.storeclient.model.LoggedInUser
import com.example.storeclient.model.LoginRequest
import okhttp3.OkHttpClient
import com.example.storeclient.BuildConfig
import com.example.storeclient.data.interceptors.EncryptionInterceptor
import com.example.storeclient.data.interceptors.HeaderInterceptor
import com.example.storeclient.crypto.DecryptingConverterFactory
import com.example.storeclient.crypto.EncryptingConverterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoggedInUser

    @POST("auth/get_Keys")
    suspend fun createProduct(@Body product: LoginRequest): CryptoKeys

    @GET("products/{id}")
    suspend fun getOneProduct(@Path("id") id: String): ProductsItem

    @GET("products")
    suspend fun getAllProducts(): Products

    @GET("users/{id}")
    suspend fun getOneUser(@Path("id") id: String): UsersItem

    @GET("users")
    suspend fun getAllUsers(): Users

    @DELETE("products/{id}")
    suspend fun eraseProduct(@Path("id") productId: String)

    @POST("products/minus/{id}")
    suspend fun deleteProduct(@Path("id") productId: String)

    @POST("products/plus/{id}")
    suspend fun addProduct(@Path("id") productId: String)

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") productId: String, @Body product: ProductsItem)

    @POST("products")
    suspend fun createProduct(@Body product: ProductsItem)
}

/**
 * yeap, not a factory, had to singletonized the thing instead...
 */
object ApiService {
    private var instance: RetrofitService? = null

    fun makeRetrofitService(): RetrofitService {
        if (instance == null) {
            val gsonFactory = GsonConverterFactory.create()
            val decryptingFactory = DecryptingConverterFactory(gsonFactory)
            val encryptingFactory = EncryptingConverterFactory(gsonFactory)

            instance = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(decryptingFactory)
                .addConverterFactory(encryptingFactory)
                .client(getClient())
                .build()
                .create(RetrofitService::class.java)
        }
        return instance!!
    }
    
    private fun getClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
        return client
    }
}
