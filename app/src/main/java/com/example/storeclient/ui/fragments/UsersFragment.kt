package com.example.storeclient.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.storeclient.R
import com.example.storeclient.data.RetrofitServiceFactory
import com.example.storeclient.entities.Users
import com.example.storeclient.ui.adapters.UsersRecyclerViewAdapter
import com.example.storeclient.ui.base.BaseFragment
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
        val service = RetrofitServiceFactory.makeRetrofitService()

        lifecycleScope.launch {
            try {
                val list_users = service.getAllUsers()

                recyclerView.layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                recyclerView.adapter = UsersRecyclerViewAdapter(list_users)
            } catch (e: Exception) {
                Log.e("retrofit_error", "Error al obtener productos", e)
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
