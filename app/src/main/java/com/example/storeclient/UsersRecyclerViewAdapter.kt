package com.example.storeclient

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.example.storeclient.placeholder.PlaceholderContent.PlaceholderItem
import com.example.storeclient.databinding.FragmentUsersBinding
import com.example.storeclient.entities.Users
import com.example.storeclient.entities.UsersItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class UsersRecyclerViewAdapter(
    private val values: List<UsersItem>
) : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    private lateinit var mainActivity: MainActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        mainActivity = context as MainActivity
        return ViewHolder(
            FragmentUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.userId.toString()
        holder.contentView.text = item.name
//so creating a click listener,  dont need to register data of user, as im not doing the most used by everyuser
        holder.itemView.setOnClickListener {

            Log.i("UsersAdapter", "Clicado: ${item.name}")
            Toast.makeText(holder.itemView.context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
            mainActivity.navigate(AppFragments.PRODUCTS_FRAGMENT)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}