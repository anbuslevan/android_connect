package com.example.connect.domain.model.request

class MobileAuthRequest (
    val mobileNumber: String,
    val deviceToken: String
)

class TwoFARequest (
    val mobileNumber: String,
    val otp: String,
    val deviceToken: String
)

class AuthRequest (
    val encryptedContext: String
)