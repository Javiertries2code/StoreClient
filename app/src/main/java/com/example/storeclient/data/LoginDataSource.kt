package com.example.storeclient.data

import android.content.Context
import com.example.storeclient.model.LoggedInUser
import com.example.storeclient.model.LoginRequest
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val context: Context){

     suspend fun login(email: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val service = ApiService.makeRetrofitService(context)
            val request = LoginRequest(email , password)

            val loggedUser = service.login(request)


            return Result.Success(loggedUser)

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}