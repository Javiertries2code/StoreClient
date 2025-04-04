package com.example.storeclient.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.storeclient.R
import com.example.storeclient.ui.base.BaseFragment

class AdminUsersFragment :  BaseFragment(R.layout.fragment_dispatch_note) {

    companion object {
        fun newInstance() = AdminUsersFragment()
    }

    private val viewModel: AdminUsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }


}