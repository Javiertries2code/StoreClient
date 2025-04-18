package com.example.storeclient.ui.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeclient.data.ApiService
import com.example.storeclient.entities.ProductsItem
import com.example.storeclient.enums.FilterType
import com.example.storeclient.helpers.EmailHelper
import com.example.storeclient.utils.ExcelExporter
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val service = ApiService.makeRetrofitService(application.applicationContext)

    private val fullProductList = mutableListOf<ProductsItem>()

    private val _filteredProducts = MutableLiveData<List<ProductsItem>>()
    val filteredProducts: LiveData<List<ProductsItem>> = _filteredProducts

    init {
        loadProducts()
    }

    /**
     * Loads the full list of products from the Retrofit service and stores them in memory.
     * The loaded products are also exposed to the UI via LiveData.
     * In case of an error during the fetch, logs the exception.
     */
    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = service.getAllProducts()
                fullProductList.clear()
                fullProductList.addAll(products)
                _filteredProducts.value = fullProductList
            } catch (e: Exception) {
                Log.e("Debug", "InventoryViewModel -Error al cargar productos", e)
            }
        }
    }

    /**
     * Applies a filter to the list of products and updates the filtered LiveData accordingly.
     *
     * @param filter The type of filter to apply: ALL, LOW_STOCK, or DISABLED.
     */
    fun applyFilter(filter: FilterType) {
        _filteredProducts.value = when (filter) {
            FilterType.ALL -> fullProductList
            FilterType.LOW_STOCK -> fullProductList.filter { it.amount < it.minimumAmount }
            FilterType.DISABLED -> fullProductList.filter { it.enabled == 0 }
        }
    }

    private fun buildInventoryReport(products: List<ProductsItem>): String {
        val report = StringBuilder()
        report.append("Producto".padEnd(30)+"\t")
        report.append("Cantidad".padEnd(30)+"\t")
        report.append("Stock Mínimo".padEnd(30) + "\n")

        for (item in products) {
            report.append(item.name.padEnd(30)+"\t")
            report.append(item.amount.toString().padEnd(30)+"\t")
            report.append(item.minimumAmount.toString().padEnd(30) + "\n")
        }

        return report.toString()
    }

    fun sendInventoryEmail(context: Context) {
        val products = _filteredProducts.value ?: emptyList()
        val file = ExcelExporter.exportInventoryToExcel(context, products)
        val bodyText = buildInventoryReport(products)

        if (file != null) {
            viewModelScope.launch {
                EmailHelper.sendInventoryEmail(context, file, bodyText)
            }
        } else {
            Log.e("InventoryViewModel", "No se pudo crear el archivo Excel para enviar.")
        }
    }



}
