package com.example.storeclient.data.interceptors

import com.example.storeclient.data.auth.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val access_token = TokenManager.getAccessToken()?: ""
        val refresh_token = TokenManager.getAccessToken()?: ""
        val request = chain.request().newBuilder().addHeader(
            "Accept", "application/json"
        )
            .addHeader(
                "acess_token", access_token
            )
            .addHeader("refresh_token", refresh_token)
    .build()

    return chain.proceed(request)
}
}