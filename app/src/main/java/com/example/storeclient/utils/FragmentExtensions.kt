package com.example.storeclient.utils

import androidx.fragment.app.Fragment
import com.example.storeclient.AppFragments
import com.example.storeclient.MainActivity

fun Fragment.navigateTo(fragment: AppFragments) {
    val activity = activity as? MainActivity
    if(fragment != AppFragments.LANDING_FRAGMENT)
    { activity?.lastFragment = fragment}
    activity?.navigate(fragment)
}

fun Fragment.SecureNavigateTo(fragment: AppFragments) {
    val activity = activity as? MainActivity
//    if(fragment != AppFragments.LANDING_FRAGMENT)
//    {  activity?.lastFragment = fragment}
    activity?.secureNavigate(fragment)
}
//Update the AppFRagments to signal is the fragment is protected or not
fun Fragment.smartNavigateTo(fragment: AppFragments) {
    val main = activity as? MainActivity ?: return
    if (fragment.isProtected) {
        main.secureNavigate(fragment)
    } else {
        main.navigate(fragment)
    }
}

fun Fragment.goToProducts() {
    smartNavigateTo(AppFragments.PRODUCTS_FRAGMENT)
}

fun Fragment.goToUsers() {
    smartNavigateTo(AppFragments.USERS_FRAGMENT)
}

fun Fragment.goToLanding() {
    smartNavigateTo(AppFragments.LANDING_FRAGMENT)
}