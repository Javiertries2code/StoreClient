package com.example.storeclient.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storeclient.databinding.ItemPublicProductBinding
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.ui.viewmodels.ProductsViewModel

class PublicProductsAdapter(private val viewModel: ProductsViewModel) :
    ListAdapter<ProductsItem, PublicProductsAdapter.ProductViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemPublicProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemPublicProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductsItem) {
            binding.productName.setText(product.name)
            binding.productAmount.setText(product.amount.toString())
            binding.productMinimum.setText(product.minimumAmount.toString())
            //as for what mysql uses
            if(product.enabled == 1 )
                binding.productEnabled.isChecked = true
            else
                binding.productEnabled.isChecked = false

            // TODO: load product.image into binding.productImage using Glide or similar if needed

            binding.buttonEdit.setOnClickListener {
                var check_enabled = 0
                if(binding.productEnabled.isChecked)
                    check_enabled = 1

                val updatedProduct = product.copy(
                    name = binding.productName.text.toString(),
                    minimumAmount = binding.productMinimum.text.toString().toIntOrNull() ?: product.minimumAmount,

                      enabled = check_enabled
                )
                viewModel.editProduct(updatedProduct)
                Toast.makeText(binding.root.context, "Producto editado", Toast.LENGTH_SHORT).show()
            }

            binding.buttonDisable.setOnClickListener {
                val updatedProduct = product.copy(enabled = 0)//as for what mysql uses
                viewModel.editProduct(updatedProduct)
                Toast.makeText(binding.root.context, "Producto deshabilitado", Toast.LENGTH_SHORT).show()
            }

            binding.buttonDelete.setOnClickListener {
                viewModel.eraseProduct(product.productId)
                Toast.makeText(binding.root.context, "Producto eliminado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductsItem>() {
        override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean =
            oldItem.productId == newItem.productId

        override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean =
            oldItem == newItem
    }
}