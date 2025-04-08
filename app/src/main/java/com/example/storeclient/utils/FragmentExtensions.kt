package com.example.storeclient.utils

import androidx.fragment.app.Fragment
import com.example.storeclient.AppFragments
import com.example.storeclient.MainActivity

fun Fragment.navigateTo(fragment: AppFragments) {
    (activity as? MainActivity)?.navigate(fragment)
}

fun Fragment.SecureNavigateTo(fragment: AppFragments) {
    (activity as? MainActivity)?.secureNavigate(fragment)
}
