package com.example.storeclient.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.storeclient.R
import com.example.storeclient.databinding.FragmentAddProductBinding
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.ui.base.BaseFragment

class Add_Product_Fragment : BaseFragment(R.layout.fragment_add__product_) {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            binding.imageButton.setImageBitmap(bitmap)
            // Aquí podrías guardar la imagen como Bitmap, Uri o ByteArray si lo necesitas
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            takePictureLauncher.launch(null)
        }

        binding.buttonSave.setOnClickListener {
            saveProduct()
        }
    }

    private fun saveProduct() {
        val name = binding.editName.text.toString()
        val amount = binding.editAmount.text.toString().toIntOrNull()
        val minimumAmount = binding.editMinimumAmount.text.toString().toIntOrNull()
        val cost = binding.editCost.text.toString().toDoubleOrNull()
        val retailPrice = binding.editRetailPrice.text.toString().toDoubleOrNull()
        val season = binding.editSeason.text.toString().toIntOrNull()
        val enabled = binding.checkboxEnabled.isChecked

        if (name.isBlank() || amount == null || minimumAmount == null || cost == null || retailPrice == null || season == null) {
            Toast.makeText(requireContext(), "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
            return
        }

        val product = ProductsItem(
            name = name,
            amount = amount,
            minimumAmount = minimumAmount,
            cost = cost,
            retailPrice = retailPrice,
            season = if (season != 0) 1 else 0,
            enabled = if (enabled) 1 else 0
            // image is left null
        )

        Toast.makeText(requireContext(), "Product ready to send: $product", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
