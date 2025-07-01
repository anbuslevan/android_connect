package com.example.connect.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileAuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    var phoneNumber by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun sendOTP(navController: NavController) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = authRepository.requestOTP(phoneNumber)

                when (response) {
                    is NetworkResponse.Success -> {
                        val message = response.body?.message
                        Log.d("Login successful", "token: ${message}")
                    }

                    is NetworkResponse.Error -> {
                        val error = response.body.error?.get(0)
                        Log.d("Login failed", "error: ${error}")
                    }
                }
            } catch (exception: Exception) {
                error = exception.localizedMessage ?: "Something went wrong"
            }
        }
    }
}