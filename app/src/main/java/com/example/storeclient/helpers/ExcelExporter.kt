
package com.example.storeclient.utils

import android.content.Context
import android.util.Log
import com.example.storeclient.entities.ProductsItem
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

object ExcelExporter {

    fun exportInventoryToExcel(
        context: Context,
        products: List<ProductsItem>
    ): File? {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Inventario")

        val header = sheet.createRow(0)
        header.createCell(0).setCellValue("Producto")
        header.createCell(1).setCellValue("Cantidad")
        header.createCell(2).setCellValue("Stock Mínimo")

        products.forEachIndexed { index, product ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(product.name)
            row.createCell(1).setCellValue(product.amount.toDouble())
            row.createCell(2).setCellValue(product.minimumAmount.toDouble())
        }

        // Autoajuste de columnas
//        for (i in 0..2) {
//            sheet.autoSizeColumn(i)
//        }

        return try {
            val file = File(context.cacheDir, "inventario.xlsx")
            FileOutputStream(file).use { workbook.write(it) }
            workbook.close()
            file
        } catch (e: Exception) {
            Log.e("ExcelExporter", "Error al crear Excel", e)
            null
        }
    }
}
