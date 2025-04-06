package com.example.storeclient

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.storeclient.databinding.ActivityMainBinding
import androidx.fragment.app.replace
import com.example.storeclient.data.LoginDataSource
import com.example.storeclient.data.LoginRepository
import com.example.storeclient.ui.fragments.UsersFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding

    //fragmento to navogate afterloggin
    public lateinit var targetFragment: AppFragments

// as to laod in on successfull login, else, idfk how to pass it from the drawer's clicks
lateinit var loginRepository: LoginRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//initializing bindin
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //seeting up fragment
        if (savedInstanceState == null) {

////HERE I fit it the  initial fragment, testing login now, it whould be landing
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UsersFragment>(R.id.fragmentContainer, )

            }
        }
        //TestRetroFit
//
//        val service = RetrofitServiceFactory.makeRetrofitService()
//
//        lifecycleScope.launch { val products = service.getOneProduct("2")
//            Log.i("resultreceived", products.toString())
//
//        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    //I hope this one handles the login change, along passing a bundle if needed
    /**
     * It handles the navigacion between fragments, returning the same instance of the fragment.
     * We could pass a bundle as an argument if needed
     */
    fun navigate(fragment: AppFragments, args: Bundle? = null) {
        val fragmentManager = supportFragmentManager
        val existingFragment = fragmentManager.findFragmentByTag(fragment.name)

        val fragmentToNavigate = existingFragment ?: fragment.newInstance().apply {
            arguments = args
        }

        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragmentToNavigate, fragment.name)
            .addToBackStack(null)
            .commit()
    }

    /**
     * this will be the navigating function for protected routes, checking the logging...
     * Still not sure how i will make it safe  for the goback button
     * it will checked if user is logged, if not, will safe  the fragment target and navigate after sucessfull login
     *
     * gotta protect the intialitaion or it would crash in the first go i believe.
     */
    fun secureNavigate(toFragment: AppFragments) {

        Log.d("secureNavigate", "isLoggedIn: ${if (::loginRepository.isInitialized) loginRepository.isLoggedIn else "not initialized"}")

        if (::loginRepository.isInitialized && loginRepository.isLoggedIn) {
            navigate(toFragment)
        } else {
            targetFragment = toFragment
            navigate(AppFragments.LOGIN_FRAGMENT)
        }
    }

    fun logged_limit() {
        GlobalScope.launch {
            delay(10 * 20 * 50)

            loginRepository.logout()
//just to test it, will remove later... or not, who cares
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Session expired", Toast.LENGTH_LONG).show()

              //  navigate(AppFragments.LOGIN_FRAGMENT)
            }
        }
    }

    fun ensureLoginRepository() {
        if (!::loginRepository.isInitialized) {
            loginRepository = LoginRepository(LoginDataSource())
        }
    }



}