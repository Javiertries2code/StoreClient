package com.example.storeclient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storeclient.data.RetrofitServiceFactory
import com.example.storeclient.entities.Products
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.ui.products.ProductsAdapter
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class ProdutcsFragment : Fragment() {

    private lateinit var list_products: Products
    private var columnCount = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter

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
        return inflater.inflate(R.layout.fragment_produtcs_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = if (columnCount <= 1)
            LinearLayoutManager(context)
        else
            GridLayoutManager(context, columnCount)

        val service = RetrofitServiceFactory.makeRetrofitService()

        lifecycleScope.launch {
            try {
                list_products = service.getAllProducts()
                productsAdapter = ProductsAdapter(list_products)
                recyclerView.adapter = productsAdapter
            } catch (e: Exception) {
                Log.e("retrofit_error", "Error al obtener productos", e)
            }
        }

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val product = list_products[position]

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        Log.i("Swipe", "Swiped LEFT on ${product.name}")
                    }
                    ItemTouchHelper.RIGHT -> {
                        Log.i("Swipe", "Swiped RIGHT on ${product.name}")
                    }
                }

                productsAdapter.notifyItemChanged(position)
            }
        }

        ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView)
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            ProdutcsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
