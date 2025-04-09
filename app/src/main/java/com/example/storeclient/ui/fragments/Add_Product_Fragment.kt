package com.example.storeclient.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
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

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            binding.imageButton.setImageBitmap(bitmap)
            // Aquí podrías guardar la imagen como Bitmap, Uri o ByteArray si lo necesitas
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentFrame = view.findViewById<ViewGroup>(R.id.base_content_frame)
        val actualContentView = contentFrame.getChildAt(0)
        _binding = FragmentAddProductBinding.bind(actualContentView)

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
            // image is left null by now
        )

        Toast.makeText(requireContext(), "Product ready to send: $product", Toast.LENGTH_LONG).show()
        viewModel.createProduct(product);
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


/**
 * SI LO DICE CHAT...
 * ✅ 2. El truco del getChildAt(0) — ¿Qué es y por qué lo usamos?
 * 📦 Contexto
 * Tu BaseFragment infla una vista fragment_base.xml, que incluye:
 *
 * Toolbar
 *
 * Drawer
 *
 * Y un FrameLayout (base_content_frame) donde se infla el layout del fragmento real (por ejemplo, fragment_add_product.xml).
 *
 * 🧩 El problema
 * Cuando haces onViewCreated(view: View) en tu fragmento, ese view es la vista raíz de fragment_base.xml, no de fragment_add_product.xml.
 *
 * Pero tú necesitas hacer binding sobre fragment_add_product.xml.
 * Entonces, ¿cómo accedes a ese layout si está embebido dentro del otro?
 *
 * 🪄 El truco: getChildAt(0)
 * kotlin
 * Copy
 * Edit
 * val contentFrame = view.findViewById<ViewGroup>(R.id.base_content_frame)
 * val actualContentView = contentFrame.getChildAt(0)
 * _binding = FragmentAddProductBinding.bind(actualContentView)
 * 🔍 ¿Qué hace esto?
 * view.findViewById(...) busca el FrameLayout que usaste como contenedor (base_content_frame).
 *
 * .getChildAt(0) obtiene la vista inflada dentro de ese contenedor, es decir, tu fragment_add_product.
 *
 * Y finalmente FragmentAddProductBinding.bind(...) enlaza ese layout con tu binding.
 *
 * 🧠 ¿Cuándo se usa?
 * ✅ Cuando estás usando un BaseFragment que infla un layout común (toolbar, drawer, etc.)
 *
 * ✅ Y luego inyectas un fragmento de contenido dentro de un FrameLayout dentro de ese base.
 *
 * ✅ Y estás usando ViewBinding.
 *
 * 📌 Este patrón es común en apps con diseño modular o con layouts que comparten cabecera o navegación.
 *
 * 🚫 ¿Cuándo no es necesario?
 * Si tu fragmento no tiene layout base y solo inflas tu propio XML como root (como un fragmento normal), entonces simplemente harías:
 */