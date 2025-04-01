package com.example.storeclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.storeclient.data.RetrofitServiceFactory
import com.example.storeclient.databinding.FragmentLandingBinding
import com.example.storeclient.entities.Products
import com.example.storeclient.entities.ProductsItem
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [LandingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LandingFragment : Fragment() {
private lateinit var test_products: ProductsItem;
    private lateinit var list_products: Products;

    //binding
    private var _binding:FragmentLandingBinding? = null
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val service = RetrofitServiceFactory.makeRetrofitService()
        val activity = requireActivity() as? MainActivity

        binding.listProducts.setOnClickListener{
            if (activity != null) {
                activity.navigate(AppFragments.PRODUCTS_FRAGMENT)
            }


        }

        //TEsting the button and the call to server
        binding.testButton.setOnClickListener {
          //  Toast.makeText(activity, "GOAL", Toast.LENGTH_LONG).show()
            //TestRetroFit
       // val service = RetrofitServiceFactory.makeRetrofitService()

            lifecycleScope.launch {
                try {
                    val test_products = service.getOneProduct("2")
                    Log.i("resultreceived", test_products.toString())
                } catch (e: Exception) {
                    Log.e("retrofit_error", "Something went wrong", e)
                }
            }

            ///
        }
    }


}