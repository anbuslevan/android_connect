package com.example.connect.presentation.ui.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.domain.usecase.AuthUseCase
import com.example.connect.network.adapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
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
            try {
                val response = authUseCase.requestOTP(state.mobileNumber)

                if(response is NetworkResponse.Success){
                    state = state.copy(
                        isLoading = false,
                        isOTPSent = true,
                        stateOfAuth = StateOfAuth.OTP,
                        error = null
                    )
                } else if(response is NetworkResponse.Error){
                    state = state.copy(
                        isLoading = false,
                        error = "Error in sending OTP"
                    )
                }
            } catch (exception: Exception){
                exception.printStackTrace()
            }
        }
    }

    fun onVerifyOTP(){
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val response = authUseCase.verifyOTP(state.mobileNumber, state.otp)

                if(response is NetworkResponse.Success){
                    state = state.copy(
                        isLoading = false,
                        isOTPSent = true,
                        stateOfAuth = StateOfAuth.OTP,
                        error = null
                    )
                } else if(response is NetworkResponse.Error){
                    state = state.copy(
                        isLoading = false,
                        error = "Incorrect OTP"
                    )
                }
            } catch (exception: Exception){
                Log.d("_SocketTimeoutException", "Caught")
            }
        }
    }

    suspend fun getServerPublicKey(){
        authUseCase.fetchServersPublicKey()
    }

    fun generateDeviceId(){
        authUseCase.generateDeviceId()
    }
}