package com.example.connect.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun login(navController: NavController){
        viewModelScope.launch {
            loading = true
            try {
                when (val response = authRepository.login(email, password)) {
                    is NetworkResponse.Success -> {
                        val message = response.body?.message
                        Log.d("Login successful", "token: ${message}")
                    }

                    is NetworkResponse.Error -> {
                        error = response.body.error?.get(0)
                        Log.d("Login failed", "error: ${error}")
                    }
                }
            } catch (exception: Exception){
                error = exception.localizedMessage ?: "Something went wrong"
            } finally {
                loading = false
            }
        }
    }
}