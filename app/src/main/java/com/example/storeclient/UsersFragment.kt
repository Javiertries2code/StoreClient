package com.example.storeclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.storeclient.data.RetrofitServiceFactory
import com.example.storeclient.entities.Products
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.entities.Users
import com.example.storeclient.placeholder.PlaceholderContent
import com.example.storeclient.ui.products.ProductsAdapter
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class UsersFragment : Fragment() {


    private lateinit var list_products: Users;

    private var columnCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_users_list, container, false)

        val service = RetrofitServiceFactory.makeRetrofitService()

        if (view is RecyclerView) {
            lifecycleScope.launch {
                try {
                    val list_users = service.getAllUsers()

                    with(view) {
                        layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        adapter = UsersRecyclerViewAdapter(list_users)
                    }
                } catch (e: Exception) {
                    Log.e("retrofit_error", "Error al obtener productos", e)
                }
            }
        }

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}