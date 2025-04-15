package com.example.storeclient.enums



import androidx.fragment.app.Fragment
import com.elorrieta.alumnoclient.LandingFragment
import com.example.storeclient.ui.fragments.Add_Product_Fragment
import com.example.storeclient.ui.fragments.AdminFragment
import com.example.storeclient.ui.fragments.AdminUsersFragment
import com.example.storeclient.ui.fragments.DispatchNoteFragment
import com.example.storeclient.ui.fragments.InventoryFragment
import com.example.storeclient.ui.fragments.ProdutcsFragment
import com.example.storeclient.ui.fragments.PublicProductsFragment
import com.example.storeclient.ui.fragments.TestFragment
import com.example.storeclient.ui.fragments.UsersFragment
import com.example.storeclient.ui.login.LoginFragment

enum class AppFragments(
    val fragmentClass: Class<out Fragment>,
    val isProtected: Boolean = true // default to protected
) {
    TEST_FRAGMENT(TestFragment::class.java, false),
    DEV_FRAGMENT(ProdutcsFragment::class.java, true),
    LOGIN_FRAGMENT(LoginFragment::class.java, false),
    LANDING_FRAGMENT(LandingFragment::class.java, false),
    USERS_FRAGMENT(UsersFragment::class.java, false),
    PRODUCTS_FRAGMENT(ProdutcsFragment::class.java, false),
    ADMIN_FRAGMENT(AdminFragment::class.java),
    ADMIN_USERS_FRAGMENT(AdminUsersFragment::class.java),
    DISPATCH_NOTE_FRAGMENT(DispatchNoteFragment::class.java),
    INVENTORY_FRAGMENT(InventoryFragment::class.java),
    ADD_PRODUCT_FRAGMENT(Add_Product_Fragment::class.java, false),
    PUBLIC_PRODUCTS_FRAGMENT(PublicProductsFragment::class.java, true);


    fun newInstance(): Fragment {
        return fragmentClass.newInstance()
    }
}
