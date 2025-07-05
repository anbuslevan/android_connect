package com.example.connect.model.request

class MobileAuthRequest (
    val mobileNumber: String
)

class TwoFARequest (
    val mobileNumber: String,
    val otp: String
)

class AuthRequest (
    val encryptedContext: String
)