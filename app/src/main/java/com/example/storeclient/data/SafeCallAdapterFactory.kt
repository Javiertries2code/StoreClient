package com.example.storeclient.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.storeclient.model.ApiResponse
import retrofit2.*
import java.lang.reflect.Type
import okio.Timeout


class SafeCallAdapterFactory(private val context: Context) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val delegate = retrofit.nextCallAdapter(this, returnType, annotations)
        return object : CallAdapter<Any, Call<Any>> {
            override fun responseType(): Type = delegate.responseType()

            @Suppress("UNCHECKED_CAST")
            override fun adapt(call: Call<Any>): Call<Any> {
                return object : Call<Any> {
                    override fun timeout(): Timeout {
                        return call.timeout()
                    }

                    override fun enqueue(callback: Callback<Any>) {
                        call.enqueue(object : Callback<Any> {
                            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                                val body = response.body()
                                if (body is ApiResponse<*> && body.success == false) {
                                    Handler(Looper.getMainLooper()).post {

                                        Log.d("errorCode","safe call adapter$body.status -- $body.message")
                                        Toast.makeText(
                                            context,
                                            "[${body.status}] ${body.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    // Se ignora la respuesta
                                    callback.onResponse(call, Response.success(null))
                                } else {
                                    callback.onResponse(call, response)
                                }
                            }

                            override fun onFailure(call: Call<Any>, t: Throwable) {
                                callback.onFailure(call, t)
                            }
                        })
                    }

                    override fun isExecuted() = call.isExecuted
                    override fun clone() = adapt(call.clone())
                    override fun isCanceled() = call.isCanceled
                    override fun cancel() = call.cancel()
                    override fun execute(): Response<Any> = throw NotImplementedError("Use enqueue() instead")
                    override fun request() = call.request()

                }
            }
        }
    }
}
