package com.example.storeclient.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.storeclient.enums.AppFragments
import com.example.storeclient.ui.main.MainActivity
import com.example.storeclient.R
import com.example.storeclient.config.AppConfig
import com.example.storeclient.ui.fragments.base.BaseFragment
import com.example.storeclient.ui.viewmodels.AdminUsersViewModel

class AdminUsersFragment :  BaseFragment(R.layout.fragment_dispatch_note) {

    companion object {
        fun newInstance() = AdminUsersFragment()
    }

    private val viewModel: AdminUsersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity

        if (AppConfig.DEV_SKIP_LOGIN == false) {
        val repo = activity.loginRepository
        repo.loggedInUser.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                Log.d("AuthObserver", "User is null, navigating to login")
                activity.navigate(AppFragments.LOGIN_FRAGMENT)
            }
        }
        }
    }


}