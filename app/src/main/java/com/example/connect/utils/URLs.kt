package com.example.connect.utils

object URL {
    object Auth {
        private const val Base = "auth"

        const val GetPublicKey = "$Base/public-key"
        const val RequestOtp = "$Base/request-otp"
        const val VerifyOtp = "$Base/verify-otp"
    }
}
