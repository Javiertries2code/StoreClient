package com.example.storeclient.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storeclient.ui.base.BaseFragment
import com.example.storeclient.ui.adapters.ProductsAdapter
import com.example.storeclient.ui.viewmodels.ProductsViewModel
import androidx.fragment.app.viewModels
import com.example.storeclient.AppFragments
import com.example.storeclient.R
import com.example.storeclient.utils.navigateTo

class ProdutcsFragment : BaseFragment(R.layout.fragment_produtcs_list) {

    private var columnCount = 4
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private val viewModel: ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = if (columnCount <= 1)
            LinearLayoutManager(context)
        else
            GridLayoutManager(context, columnCount)

        productsAdapter = ProductsAdapter()
        recyclerView.adapter = productsAdapter

        viewModel.products.observe(viewLifecycleOwner) { productList ->
            productsAdapter.submitList(productList)
        }

        //clicklisteners del los botones de crear productos
        view.findViewById<Button>(R.id.add_product_top).setOnClickListener {
            navigateTo(AppFragments.ADD_PRODUCT_FRAGMENT)

        }
        view.findViewById<Button>(R.id.add_product_bottom).setOnClickListener {
            navigateTo(AppFragments.ADD_PRODUCT_FRAGMENT)
        }

        viewModel.loadProducts()

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//So this ensures it cant swipe when stock goes downt to 0

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val position = viewHolder.adapterPosition
                val product = productsAdapter.currentList.getOrNull(position)

                return if (product != null) {
                    if (product.amount <= 0) {
                        // Solo permitir swipe a la derecha
                        makeMovementFlags(0, ItemTouchHelper.RIGHT)
                    } else {
                        // Permitir swipe a izquierda y derecha
                        makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
                    }
                } else {
                    0
                }
            }


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val product = productsAdapter.currentList[position]

                when (direction) {
                    ItemTouchHelper.LEFT -> {

                        viewModel.deleteProduct(product.productId!!)
                        Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        viewModel.addProduct(product.productId!!)
                        Toast.makeText(requireContext(), "Producto anadido", Toast.LENGTH_SHORT).show()
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
