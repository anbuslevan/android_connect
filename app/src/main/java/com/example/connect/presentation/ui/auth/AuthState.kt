package com.example.connect.presentation.ui.auth

data class AuthState (
    val mobileNumber: String = "",
    val otp: String = "",
    val isLoading: Boolean = false,
    val canSubmit: Boolean = false,
    val error: String? = null,
    var isOTPSent: Boolean = false,
    val stateOfAuth: StateOfAuth? = StateOfAuth.MOBILE_NUMBER
)

enum class StateOfAuth {
    MOBILE_NUMBER,
    OTP
}

enum class NavDestination {

}