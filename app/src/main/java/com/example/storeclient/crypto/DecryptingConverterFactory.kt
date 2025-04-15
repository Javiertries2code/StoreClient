package com.example.storeclient.crypto

import android.util.Log
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.entities.UsersItem
import com.example.storeclient.model.LoggedInUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class DecryptingConverterFactory(
    private val baseFactory: Converter.Factory
) : Converter.Factory() {

    private val gson = Gson()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val gsonConverter = baseFactory.responseBodyConverter(type, annotations, retrofit)

        return Converter<ResponseBody, Any> { encryptedBody ->
            val encryptedText = encryptedBody.string().trim()

            Log.d("Decrypting", "🔐 Encrypted text received:\n$encryptedText")

            val decryptedJson = try {
                if (encryptedText.startsWith("{") || encryptedText.startsWith("[")) {
                    Log.d("Decrypting", "🟢 Plain JSON detected, skipping decryption")
                    encryptedText
                } else {
                    Log.d("Decrypting", "🔐 Encrypted Base64 detected, proceeding to decrypt")
                    CryptoHelper.decrypt(encryptedText)
                }
            } catch (e: Exception) {
                Log.e("Decrypting", "❌ Error decrypting body: ${e.message}")
                throw e
            }

            Log.d("Decrypting", "✅ Decrypted JSON:\n$decryptedJson")

            // Extraer solo el campo 'data' desde el ApiResponse<T>
            val jsonObject = JSONObject(decryptedJson)
            val typeName = jsonObject.optString("type")
            val dataJson = jsonObject.opt("data").toString()

            Log.d("Response","in class DecryptingConverterFactory(\n $dataJson)")

            val result: Any = when (typeName) {
                "listuser" -> gson.fromJson(dataJson, object : TypeToken<List<UsersItem>>() {}.type)
                "user" -> gson.fromJson(dataJson, UsersItem::class.java)
                "listproduct" -> gson.fromJson(dataJson, object : TypeToken<List<ProductsItem>>() {}.type)
                "product" -> gson.fromJson(dataJson, ProductsItem::class.java)
                "token" -> gson.fromJson(dataJson, LoggedInUser::class.java)

                else -> {
                    Log.e("Decrypting", "❌ Unknown type: $typeName")
                    throw IllegalArgumentException("Unknown type: $typeName")
                }
            }

            result
        }
    }
}
