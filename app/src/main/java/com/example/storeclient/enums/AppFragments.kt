package com.example.storeclient.enums

import androidx.fragment.app.Fragment
import com.elorrieta.alumnoclient.LandingFragment
import com.example.storeclient.ui.fragments.*
import com.example.storeclient.ui.login.LoginFragment

enum class AppFragments(
    val fragmentFactory: () -> Fragment,
    val isProtected: Boolean = true
) {
    TEST_FRAGMENT({ TestFragment() }, false),
    DEV_FRAGMENT({ ProdutcsFragment() }, true),
    LOGIN_FRAGMENT(::LoginFragment, false),
    LANDING_FRAGMENT({ LandingFragment() }, false),
    USERS_FRAGMENT({ UsersFragment() }, false),
    PRODUCTS_FRAGMENT({ ProdutcsFragment() }, false),
    ADMIN_FRAGMENT({ AdminFragment() }),
    ADMIN_USERS_FRAGMENT({ AdminUsersFragment() }),
    DISPATCH_NOTE_FRAGMENT({ DispatchNoteFragment() }),
    INVENTORY_FRAGMENT({ InventoryFragment() }),
    ADD_PRODUCT_FRAGMENT({ Add_Product_Fragment() }, false),
    PUBLIC_PRODUCTS_FRAGMENT({ PublicProductsFragment() }, true);

    fun newInstance(): Fragment = fragmentFactory()
}
