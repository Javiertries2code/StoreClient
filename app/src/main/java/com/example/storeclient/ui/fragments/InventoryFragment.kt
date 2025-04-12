package com.example.storeclient.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storeclient.enums.AppFragments
import com.example.storeclient.ui.main.MainActivity
import com.example.storeclient.databinding.FragmentInventoryBinding
import com.example.storeclient.enums.FilterType
import com.example.storeclient.ui.adapters.InventoryAdapter
import com.example.storeclient.ui.fragments.base.BaseFragment
import com.example.storeclient.ui.viewmodels.InventoryViewModel
import com.example.storeclient.R
import com.example.storeclient.config.AppConfig
import com.example.storeclient.utils.goToUsers

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
        binding.sendEmail.setOnClickListener {     viewModel.sendInventoryEmail(requireContext())
        }
        binding.goBack.setOnClickListener { goToUsers()}

        viewModel.filteredProducts.observe(viewLifecycleOwner) { filteredProducts ->
            adapter.submitList(filteredProducts)
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
