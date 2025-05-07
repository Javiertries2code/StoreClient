package com.example.storeclient.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeclient.data.ApiService
import com.example.storeclient.entities.ProductsItem
import kotlinx.coroutines.launch

class ProductsViewModel(application: Application) : AndroidViewModel(application){

    private val _products = MutableLiveData<List<ProductsItem>>()
    val products: LiveData<List<ProductsItem>> = _products

    //para saver la respuesta del servidor
    private val _saveStatus = MutableLiveData<Boolean>()
    val saveStatus: LiveData<Boolean> = _saveStatus

    private val service = ApiService.makeRetrofitService(application.applicationContext)

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val list = service.getAllProducts()
                _products.value = list
            } catch (e: Exception) {
                Log.e("Debug", "ProductsViewModel - Error al cargar productos", e)
            }
        }
    }
    fun loadEnabledProducts() {
        viewModelScope.launch {
            try {
                val list = service.getAllProducts().filter { it.enabled != 0 }
                _products.value = list
            } catch (e: Exception) {
                Log.e("Debug", "ProductsViewModel - Error al cargar productos", e)
            }
        }
    }



    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            try {
                service.deleteProduct(productId.toString())
                loadEnabledProducts() // I load products once again, though the list adapter will find those different and replace them
            } catch (e: Exception) {
                Log.e("ProductsViewModel", "Error al eliminar producto", e)
            }
        }
    }

    fun addProduct(productId: Int) {
        viewModelScope.launch {
            try {
                service.addProduct(productId.toString())
                loadEnabledProducts() // I load products once again, though the list adapter will find those different and replace them
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

    fun createProduct(product: ProductsItem) {
        // Aquí guardarías en base de datos o red
        viewModelScope.launch {
            try {

                service.createProduct(product)
                _saveStatus.postValue(true)
            } catch (e: Exception) {
                _saveStatus.postValue(false)
            }
        }
    }
}
