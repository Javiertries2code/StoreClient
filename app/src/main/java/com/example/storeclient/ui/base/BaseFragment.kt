package com.example.storeclient.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.storeclient.R
import androidx.core.view.GravityCompat
import com.example.storeclient.AppFragments
import com.example.storeclient.MainActivity

abstract class BaseFragment(@LayoutRes private val layoutRes: Int) : Fragment() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val baseView = inflater.inflate(R.layout.fragment_base, container, false)

        // Inflar el contenido del fragmento hijo dentro del frame
        val contentContainer = baseView.findViewById<ViewGroup>(R.id.base_content_frame)
        inflater.inflate(layoutRes, contentContainer, true)

        // Configurar el toolbar
        val toolbar = baseView.findViewById<Toolbar>(R.id.base_toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_more)
        toolbar.setNavigationOnClickListener {
            openDrawer()
        }

        drawerLayout = baseView.findViewById(R.id.base_drawer_layout)

        // Drawer buttons, pending to do the liging thing
        val navUsers = baseView.findViewById<Button>(R.id.nav_users)
        val navProducts = baseView.findViewById<Button>(R.id.nav_products)
        val navDispatch = baseView.findViewById<Button>(R.id.nav_dispatch)
        val navInventory = baseView.findViewById<Button>(R.id.nav_inventory)

        val activity = requireActivity() as? MainActivity

        navUsers?.setOnClickListener {
            activity?.navigate(AppFragments.ADMIN_USERS_FRAGMENT)
            closeDrawer()
        }

        navProducts?.setOnClickListener {
            activity?.navigate(AppFragments.PUBLIC_PRODUCTS_FRAGMENT)
            closeDrawer()
        }

        navDispatch?.setOnClickListener {
            activity?.navigate(AppFragments.DISPATCH_NOTE_FRAGMENT)
            closeDrawer()
        }

        navInventory?.setOnClickListener {
            activity?.navigate(AppFragments.INVENTORY_FRAGMENT)
            closeDrawer()
        }

        return baseView
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END)
    }

    fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END)
    }
}
