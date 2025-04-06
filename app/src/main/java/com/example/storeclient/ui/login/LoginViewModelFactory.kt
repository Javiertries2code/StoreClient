package com.example.storeclient.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storeclient.MainActivity
import com.example.storeclient.data.LoginDataSource
import com.example.storeclient.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {


    /**
     * ViewModelFactory that initializes the repository in MainActivity if it hasn't been yet,
     * and provides it to the ViewModel.
     */
    class LoginViewModelFactory(private val activity: MainActivity) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // If the repository hasn't been initialized in MainActivity yet, initialize it now
            activity.ensureLoginRepository()
            return LoginViewModel(activity.loginRepository) as T

        }
    }


}

//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(
//                loginRepository = LoginRepository(
//                    dataSource = LoginDataSource()
//                )
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}