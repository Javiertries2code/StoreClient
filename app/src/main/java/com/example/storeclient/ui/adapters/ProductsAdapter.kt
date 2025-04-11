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


class ProductsAdapter :
    ListAdapter<ProductsItem, ProductsAdapter.ProductViewHolder>(DiffCallback()) {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.product_name)
        private val amount = view.findViewById<TextView>(R.id.product_amount)
        private val minimmun_amount = view.findViewById<TextView>(R.id.min_amount)
        private val image = view.findViewById<ImageView>(R.id.product_image)

        fun bind(item: ProductsItem) {
            name.text = item.name
            amount.text = item.amount.toString()
            if (item.image == null) {
                image.setImageResource(R.drawable.defaultimage) // fallback
            } else {
                image.setImageBitmap(item.image.toBitmap())
            }

            //PENDING OF INSTALLING PICASO LIBRARY AND LOAD db

if(item.enabled == 0)
{
    amount.setTextColor(Color.BLACK)
    name.setTextColor(Color.BLACK)
    minimmun_amount.text = "Producto deshabilitado"
    minimmun_amount.setTextColor(Color.BLUE)
}
else {
    if (item.amount < item.minimumAmount) {
        minimmun_amount.text = "Bajo Stock"
        minimmun_amount.setTextColor(Color.RED)
        amount.setTextColor(Color.RED)
        name.setTextColor(Color.RED)
    } else {
        amount.setTextColor(Color.BLACK)
        name.setTextColor(Color.BLACK)
        minimmun_amount.text = "Stock OK"
        minimmun_amount.setTextColor(Color.GREEN)

    }
}
//            else if(item.amount > ProductLevel.HIGH.thresold) {
//                amount.setTextColor(Color.GREEN)
//                name.setTextColor(Color.GREEN)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
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


//class ProductsAdapter(private val values: List<ProductsItem>) :
//    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.fragment_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = values[position]
//        holder.idView.text = item.productId.toString()
//        holder.contentView.text = item.name
//    }
//
//    override fun getItemCount(): Int = values.size
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val idView: TextView = view.findViewById(R.id.item_number)
//        val contentView: TextView = view.findViewById(R.id.content)
//    }
//}
