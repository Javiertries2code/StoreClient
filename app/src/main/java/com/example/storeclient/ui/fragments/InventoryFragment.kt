package com.example.storeclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeclient.databinding.FragmentInventoryBinding
import com.example.storeclient.enums.FilterType
import com.example.storeclient.ui.adapters.InventoryAdapter
import com.example.storeclient.ui.base.BaseFragment
import com.example.storeclient.ui.viewmodels.InventoryViewModel
import com.example.storeclient.R
import com.example.storeclient.databinding.FragmentPublicProductsBinding

class InventoryFragment : BaseFragment(R.layout.fragment_inventory) {

    companion object {
        fun newInstance() = InventoryFragment()
    }

    private val viewModel: InventoryViewModel by viewModels()
    private lateinit var adapter: InventoryAdapter

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = InventoryAdapter()
        binding.inventoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.inventoryRecyclerView.adapter = adapter

        binding.showAllBtn.setOnClickListener { viewModel.applyFilter(FilterType.ALL) }
        binding.showLowStockBtn.setOnClickListener { viewModel.applyFilter(FilterType.LOW_STOCK) }
        binding.showDisabledBtn.setOnClickListener { viewModel.applyFilter(FilterType.DISABLED) }
        binding.sendEmail.setOnClickListener { viewModel.sendEmailInaventory() }

        viewModel.filteredProducts.observe(viewLifecycleOwner) { filteredProducts ->
            adapter.submitList(filteredProducts)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
