package com.example.storeclient.ui.fragments


import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.storeclient.AppFragments
import com.example.storeclient.R
import com.example.storeclient.databinding.FragmentAddProductBinding
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.ui.base.BaseFragment
import com.example.storeclient.ui.viewmodels.ProductsViewModel
import com.example.storeclient.utils.navigateTo


class Add_Product_Fragment : BaseFragment(R.layout.fragment_add__product_) {
    private val viewModel: ProductsViewModel by viewModels()

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraLauncher: ActivityResultLauncher<Void?>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private var capturedImage: Bitmap? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Cámara
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                capturedImage = it
                if (_binding != null) {
                    binding.imageButton.setImageBitmap(it)
                }
            }
        }

        // Permisos
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(null)
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentFrame = view.findViewById<ViewGroup>(R.id.base_content_frame)
        val actualContentView = contentFrame.getChildAt(0)
        _binding = FragmentAddProductBinding.bind(actualContentView)

        binding.imageButton.setOnClickListener {
            checkCameraPermissionAndLaunch()
        }

        binding.buttonSave.setOnClickListener {
            saveProduct()
        }
    }

    private fun checkCameraPermissionAndLaunch() {
        val permission = android.Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(null)
        } else {
            permissionLauncher.launch(permission)
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
            // image is left null by now
        )

        Toast.makeText(requireContext(), "Product ready to send: $product", Toast.LENGTH_LONG).show()
        viewModel.createProduct(product)
        viewModel.saveStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Product saved successfully!", Toast.LENGTH_SHORT).show()
                this.navigateTo(AppFragments.PRODUCTS_FRAGMENT)
            } else {
                Toast.makeText(requireContext(), "Failed to save product", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
