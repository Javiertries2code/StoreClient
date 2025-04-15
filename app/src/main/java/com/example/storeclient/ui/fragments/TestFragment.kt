package com.example.storeclient.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.storeclient.R
import com.example.storeclient.data.ApiService
import com.example.storeclient.enums.AppFragments
import com.example.storeclient.ui.fragments.base.BaseFragment
import com.example.storeclient.utils.smartNavigateTo
import kotlinx.coroutines.launch

class TestFragment : BaseFragment(R.layout.fragment_test) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val errorCodeEditText = view.findViewById<EditText>(R.id.errorCodeEditText)
        val sendErrorButton = view.findViewById<Button>(R.id.sendErrorButton)

        sendErrorButton.setOnClickListener {
            val errorCode = errorCodeEditText.text.toString().toIntOrNull()
            if (errorCode == null) {
                Toast.makeText(requireContext(), "Enter a valid number", Toast.LENGTH_SHORT).show()
            } else {
                val service = ApiService.makeRetrofitService(requireContext())

                lifecycleScope.launch {
                    try {
                        val response = service.getErrorById(errorCode.toString()) // suspend fun
                        // puedes mostrar algo con response si quieres
                        smartNavigateTo(AppFragments.TEST_FRAGMENT)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }}




