package com.example.storeclient.ui.adapters


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storeclient.R
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.utils.toBitmap
import org.json.JSONObject.NULL


class InventoryAdapter :
    ListAdapter<ProductsItem, InventoryAdapter.ProductViewHolder>(DiffCallback()) {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.product_name)
        private val amount = view.findViewById<TextView>(R.id.product_amount)
        private val minimmun_amount = view.findViewById<TextView>(R.id.min_amount)

        fun bind(item: ProductsItem) {
            name.text = item.name

            amount.text = item.amount.toString()
            minimmun_amount.text = item.minimumAmount.toString()
            if (item.amount < item.minimumAmount) {
                amount.setTextColor(Color.RED)
            } else {
                amount.setTextColor(Color.BLACK)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductsItem>() {
        override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
            return oldItem == newItem
        }
    }
}

