package com.example.storeclient



import androidx.fragment.app.Fragment
import com.example.storeclient.ui.fragments.AdminFragment
import com.example.storeclient.ui.fragments.AdminUsersFragment
import com.example.storeclient.ui.fragments.DispatchNoteFragment
import com.example.storeclient.ui.fragments.InventoryFragment
import com.example.storeclient.ui.fragments.LandingFragment
import com.example.storeclient.ui.fragments.ProdutcsFragment
import com.example.storeclient.ui.fragments.PublicProductsFragment
import com.example.storeclient.ui.fragments.UsersFragment
import com.example.storeclient.ui.login.LoginFragment

enum class AppFragments(val fragmentClass: Class<out Fragment>) {
    LOGIN_FRAGMENT(LoginFragment::class.java),
    USERS_FRAGMENT(UsersFragment::class.java),
    PRODUCTS_FRAGMENT(ProdutcsFragment::class.java),
    LANDING_FRAGMENT(LandingFragment::class.java),
    ADMIN_FRAGMENT(AdminFragment::class.java),
    ADMIN_USERS_FRAGMENT(AdminUsersFragment::class.java),
    DISPATCH_NOTE_FRAGMENT(DispatchNoteFragment::class.java),
    INVENTORY_FRAGMENT(InventoryFragment::class.java),
    PUBLIC_PRODUCTS_FRAGMENT(PublicProductsFragment::class.java);


    fun newInstance(): Fragment {
        return fragmentClass.newInstance()
    }
}
