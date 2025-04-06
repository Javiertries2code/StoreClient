package com.example.storeclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeclient.R
import com.example.storeclient.databinding.FragmentPublicProductsBinding
import com.example.storeclient.ui.adapters.PublicProductsAdapter
import com.example.storeclient.ui.base.BaseFragment
import com.example.storeclient.ui.viewmodels.ProductsViewModel

class PublicProductsFragment : BaseFragment(R.layout.fragment_public_products) {

    private var _binding: FragmentPublicProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var adapter: PublicProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPublicProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PublicProductsAdapter(viewModel)
        binding.publicProductsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.publicProductsRecyclerView.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner) { productList ->
            adapter.submitList(productList)
        }

        viewModel.loadProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PublicProductsFragment()
    }
}
