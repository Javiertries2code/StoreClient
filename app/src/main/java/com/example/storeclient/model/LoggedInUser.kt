package com.example.storeclient.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(

    //I called it emial in token response, though here is id, seems to be string, hope it works
    val userId: String,
    val displayName: String,


    //So i collect the loging token in the way
    val access_token: String? = null,
    val refresh_token: String? = null

)