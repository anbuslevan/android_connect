package com.example.connect.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.repository.AuthRepository
import com.example.connect.utils.APIUtils
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

    fun login(onSuccess: () -> Unit){
        viewModelScope.launch {
            loading = true
            try {
                val response = authRepository.login(email, password)
                if(response.isSuccessful && response.body()?.message != null){
                    error = null
                    onSuccess()
                }
                else {
                    error = APIUtils.parse(response.errorBody()?.string())?.error?.get(0) ?: "Something went wrong"
                }
            } catch (exception: Exception){
                error = exception.localizedMessage ?: "Something went wrong"
            } finally {
                loading = false
            }
        }
    }
}