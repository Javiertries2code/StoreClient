package com.elorrieta.alumnoclient

import kotlinx.coroutines.*

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.storeclient.enums.AppFragments
import com.example.storeclient.ui.main.MainActivity
import com.example.storeclient.R
import com.example.storeclient.utils.smartNavigateTo


class LandingFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.animatedImage)
        val animation = imageView.drawable as? AnimationDrawable
        animation?.start()
//////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        GlobalScope.launch(Dispatchers.IO) {
            var myConnection = true
            while (true) {

                val activity = requireActivity() as? MainActivity
                val isConenected = activity?.isInternetAvailable()

                withContext(Dispatchers.Main) {
                    val buttonConnectionChecker = view.findViewById<Button>(R.id.buttonConnect)

                    if (isConenected == false) {
                        buttonConnectionChecker?.isEnabled = false
                        if(myConnection == true){
                            Toast.makeText(requireContext(), "NO HAY CONECTIVIDAD", Toast.LENGTH_SHORT).show()
                            myConnection = false
                        }
                        smartNavigateTo(AppFragments.LANDING_FRAGMENT)
                    } else {
                        if (myConnection== false) {
                            Toast.makeText(requireContext(), "SE HA VUELTO A CONECTAR", Toast.LENGTH_SHORT).show()
                            myConnection = true
                            if (activity != null) {
                                smartNavigateTo(activity.lastFragment)
                                Log.d("NAVIGATE", "NOTREACHING RECONNECTION navigation")

                            }
                        }
                        Log.d("NAVIGATE", "CHANGIN BUTTON")

                        buttonConnectionChecker?.isEnabled = true
                    }
                }
                delay(300)
            }
        }


////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////


        val buttonConnect = view.findViewById<Button>(R.id.buttonConnect)
        buttonConnect.setOnClickListener {
            val activity = requireActivity() as? MainActivity
            if (activity != null) {


                activity.navigate(AppFragments.USERS_FRAGMENT)
            }


        }
    }

}



