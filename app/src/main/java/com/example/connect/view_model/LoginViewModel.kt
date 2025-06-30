package com.example.connect.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.model.response.LoginResponse
import com.example.connect.repository.AuthRepository
import com.example.connect.utils.APIUtils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun login(onSuccess: () -> Unit){
        viewModelScope.launch {
            loading = true
            error = null
            try {
                val response = authRepository.login(username, email, password)
                if(response.isSuccessful && response.body()?.message != null){
                    onSuccess()
                }
                else {
                    error = APIUtils.parse(response.errorBody()?.string())?.message ?: "Something went wrong"
                }
            } catch (exception: Exception){
                error = exception.localizedMessage ?: "Something went wrong"
            } finally {
                loading = false
            }
        }
    }
}