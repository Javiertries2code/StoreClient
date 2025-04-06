package com.example.storeclient.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.storeclient.AppFragments
import com.example.storeclient.MainActivity
import com.example.storeclient.R
import com.example.storeclient.ui.base.BaseFragment

class InventoryFragment : BaseFragment(R.layout.fragment_dispatch_note)  {

    companion object {
        fun newInstance() = InventoryFragment()
    }

    private val viewModel: InventoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        val repo = activity.loginRepository
        repo.loggedInUser.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                Log.d("AuthObserver", "User is null, navigating to login")
                activity.navigate(AppFragments.LOGIN_FRAGMENT)
            }
        }
    }



}