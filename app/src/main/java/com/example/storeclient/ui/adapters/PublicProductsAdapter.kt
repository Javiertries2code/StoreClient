package com.example.storeclient.ui.adapters

import android.util.Log
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
                Log.d("PRODUCTO", "Edit button clicked")
                try {
                    val name = binding.productName.text.toString()
                    val minAmount = binding.productMinimum.text.toString().toIntOrNull()
                    val isEnabled = binding.productEnabled.isChecked

                    Log.d("PRODUCTO", "Form values: name=$name, minAmount=$minAmount, enabled=$isEnabled")

                    val updatedProduct = product.copy(
                        name = name,
                        minimumAmount = minAmount ?: product.minimumAmount,
                        enabled = if (isEnabled) 1 else 0
                    )

                    Log.d("PRODUCTO", "Calling editProduct with: $updatedProduct")
                    viewModel.editProduct(updatedProduct)
                    Toast.makeText(binding.root.context, "Producto editado", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("PRODUCTO", "Edit failed", e)
                }
            }



            binding.buttonDelete.setOnClickListener {
                viewModel.eraseProduct(product.productId!!)
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