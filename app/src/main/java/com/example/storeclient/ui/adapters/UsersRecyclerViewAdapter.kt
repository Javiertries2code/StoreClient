package com.example.storeclient.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.storeclient.enums.AppFragments
import com.example.storeclient.ui.main.MainActivity
import com.example.storeclient.R
import com.example.storeclient.entities.UsersItem

class UsersRecyclerViewAdapter(
    private val values: List<UsersItem>
) : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mainActivity: MainActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        mainActivity = context as MainActivity
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_users, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.userId.toString()
        holder.contentView.text = item.name
        if (item.image == null) {
            holder.userImage.setImageResource(R.drawable.useravatar) // fallback
        } else {
        //hold your horses, users thing is not my duty,
        // if so, gotta change userimage from byte[]  to string and so on
        //  holder.userImage.setImageBitmap(item.image.toBitmap())
        }

        holder.itemView.setOnClickListener {
            Log.i("UsersAdapter", "Clicked: ${item.name}")
            Toast.makeText(holder.itemView.context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
            mainActivity.navigate(AppFragments.PRODUCTS_FRAGMENT)

        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val userImage: ImageView = view.findViewById(R.id.user_image)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
