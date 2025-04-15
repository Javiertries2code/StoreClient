package com.example.storeclient.ui.fragments.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.storeclient.R
import androidx.core.view.GravityCompat
import com.example.storeclient.config.AppConfig
import com.example.storeclient.enums.AppFragments
import com.example.storeclient.ui.main.MainActivity
import com.example.storeclient.utils.smartNavigateTo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.delay

abstract class BaseFragment(@LayoutRes private val layoutRes: Int) : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
private   var activity : MainActivity? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val baseView = inflater.inflate(R.layout.fragment_base, container, false)

      activity  = requireActivity() as? MainActivity?
        //click reset delay counter


        // Inflar el contenido del fragmento hijo dentro del frame
        val contentContainer = baseView.findViewById<ViewGroup>(R.id.base_content_frame)
        inflater.inflate(layoutRes, contentContainer, true)

        // Configurar el toolbar
        val toolbar = baseView.findViewById<Toolbar>(R.id.base_toolbar)
        activity?.setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_more)
        toolbar.setNavigationOnClickListener {
            openDrawer()
        }

        drawerLayout = baseView.findViewById(R.id.base_drawer_layout)

        // Drawer buttons, pending to do the liging thing
        val navUsers = baseView.findViewById<Button>(R.id.nav_users)
        val navTest = baseView.findViewById<Button>(R.id.nav_test)
        val navProducts = baseView.findViewById<Button>(R.id.nav_products)
        val navDispatch = baseView.findViewById<Button>(R.id.nav_dispatch)
        val navInventory = baseView.findViewById<Button>(R.id.nav_inventory)
        val buttonHome = baseView.findViewById<ImageButton>(R.id.buttonHome)

        val activity = requireActivity() as? MainActivity

        buttonHome?.setOnClickListener {
            smartNavigateTo(AppFragments.USERS_FRAGMENT)
            closeDrawer()
        }

        navUsers?.setOnClickListener {
            smartNavigateTo(AppFragments.ADMIN_USERS_FRAGMENT)
            closeDrawer()
        }

        navProducts?.setOnClickListener {
            smartNavigateTo(AppFragments.PUBLIC_PRODUCTS_FRAGMENT)
            closeDrawer()
        }

        navDispatch?.setOnClickListener {
            smartNavigateTo(AppFragments.DISPATCH_NOTE_FRAGMENT)
            closeDrawer()
        }

        navInventory?.setOnClickListener {
            smartNavigateTo(AppFragments.INVENTORY_FRAGMENT)
            closeDrawer()
        }
        navTest?.setOnClickListener{ smartNavigateTo(AppFragments.TEST_FRAGMENT)
            closeDrawer()}

        return baseView
    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END)
    }

    fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END)
    }
}
