package com.example.connect.presentation.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.network.adapter.NetworkResponse
import com.example.connect.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    var state by mutableStateOf(AuthState())
        private set

    fun onMobileNumberChange(mobileNumber: String){
        if(mobileNumber != state.mobileNumber){
            state = state.copy(mobileNumber = mobileNumber)
        }
    }

    fun onOTPChange(otp: String) {
        if(otp != state.otp){
            state = state.copy(otp = otp)
        }
    }

    fun onRequestOTP(){
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val response = repository.requestOTP(state.mobileNumber)

            if(response is NetworkResponse.Success){
                state = state.copy(
                    isLoading = false,
                    isOTPSent = true,
                    stateOfAuth = StateOfAuth.OTP
                )
            } else if(response is NetworkResponse.Error){
                state = state.copy(
                    isLoading = false,
                    error = "Error in sending OTP"
                )
            }
        }
    }

    fun onVerifyOTP(){

    }
}