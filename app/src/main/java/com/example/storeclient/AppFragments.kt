package com.example.storeclient



import androidx.fragment.app.Fragment
import com.example.storeclient.ui.login.LoginFragment

enum class AppFragments(val fragmentClass: Class<out Fragment>) {
    LOGIN_FRAGMENT(LoginFragment::class.java),
    USERS_FRAGMENT(UsersFragment::class.java),
    PRODUCTS_FRAGMENT(ProdutcsFragment::class.java),
    LANDING_FRAGMENT(LandingFragment::class.java),
    ADMIN_FRAGMENT(AdminFragment::class.java);


    fun newInstance(): Fragment {
        return fragmentClass.newInstance()
    }
}
