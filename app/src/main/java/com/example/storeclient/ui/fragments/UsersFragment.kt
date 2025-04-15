package com.example.storeclient.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.storeclient.R
import com.example.storeclient.data.ApiService
import com.example.storeclient.entities.Users
import com.example.storeclient.ui.adapters.UsersRecyclerViewAdapter
import com.example.storeclient.ui.fragments.base.BaseFragment
import kotlinx.coroutines.launch

class UsersFragment : BaseFragment(R.layout.fragment_users_list){

    private lateinit var list_products: Users
    private var columnCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val service = ApiService.makeRetrofitService(requireContext())

        lifecycleScope.launch {
            try {
                val list_all_users = service.getAllUsers()

                if (!list_all_users.isNullOrEmpty()) {

                    val list_users = list_all_users.filter { it.enabled == 1 }

                    recyclerView.layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }
                    recyclerView.adapter = UsersRecyclerViewAdapter(list_users)
                } else {
                    Log.e("retrofit_error", "❌ API responded with error: ")
                }

            } catch (e: Exception) {
                Log.e("retrofit_error", "❌ Network or parsing error", e)
            }
        }

    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
