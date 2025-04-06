package com.example.storeclient.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeclient.data.RetrofitServiceFactory
import com.example.storeclient.entities.ProductsItem
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val _products = MutableLiveData<List<ProductsItem>>()
    val products: LiveData<List<ProductsItem>> = _products

    private val service = RetrofitServiceFactory.makeRetrofitService()

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val list = service.getAllProducts()
                _products.value = list
            } catch (e: Exception) {
                Log.e("ProductsViewModel", "Error al cargar productos", e)
            }
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            try {
                service.deleteProduct(productId.toString())
                loadProducts() // I load products once again, though the list adapter will find those different and replace them
            } catch (e: Exception) {
                Log.e("ProductsViewModel", "Error al eliminar producto", e)
            }
        }
    }

    fun addProduct(productId: Int) {
        viewModelScope.launch {
            try {
                service.addProduct(productId.toString())
                loadProducts() // I load products once again, though the list adapter will find those different and replace them
            } catch (e: Exception) {
                Log.e("ProductsViewModel", "Error al añadir producto", e)
            }
        }
    }

    fun editProduct(updatedProduct: ProductsItem) {
        viewModelScope.launch {
            try {
                service.updateProduct(updatedProduct.productId.toString(), updatedProduct)
                loadProducts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun eraseProduct(productId: Int) {
        viewModelScope.launch {
            try {
                service.eraseProduct(productId.toString())
                loadProducts() // I load products once again, though the list adapter will find those different and replace them
            } catch (e: Exception) {
                Log.e("ProductsViewModel", "Error al eliminar producto", e)
            }
        }
    }
}
