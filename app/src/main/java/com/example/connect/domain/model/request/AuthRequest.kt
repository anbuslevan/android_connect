package com.example.connect.domain.model.request

class MobileAuthRequest (
    val mobileNumber: String,
    val deviceId: String
)

class TwoFARequest (
    val mobileNumber: String,
    val otp: String,
    val deviceId: String
)

class AuthRequest (
    val encryptedContext: String
)