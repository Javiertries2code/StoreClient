package com.example.storeclient.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storeclient.AppFragments
import com.example.storeclient.MainActivity
import com.example.storeclient.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {
//a fit to avoid continuos automatic reconcetion, as for o=some reasons is was continiously loggin in, someloo somewhere is trigerring it
    var isLocked = false



    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    private val _loggedInUser = MutableLiveData<LoggedInUser?>()
    val loggedInUser: LiveData<LoggedInUser?> get() = _loggedInUser

    private fun setLoggedInUser(user: LoggedInUser) {
        this.user = user
        _loggedInUser.postValue(user)
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    fun logout() {
        user = null
        _loggedInUser.postValue(null)
        isLocked = true
        dataSource.logout()
    }



//
//    fun logout() {
//        Log.d("logout", "Logging out user: ${user?.displayName ?: "unknown"}")
//
//        user = null
//        dataSource.logout()
//        isLocked = true
//
//        Log.d("logout", "after loging out Logging out user: ${user?.displayName ?: "unknown"}")
//
//    }

    fun unlock() {
        isLocked = false
    }

     suspend fun login(username: String, password: String): Result<LoggedInUser> {


         if (isLocked) {
             Log.d("LoginRepository", "Login is locked!")
             return Result.Error(Exception("Login is locked"))
         }


         val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
            Log.d("login", "login repository -->succesfull")
            isLocked = true

        }

        return result
    }


}