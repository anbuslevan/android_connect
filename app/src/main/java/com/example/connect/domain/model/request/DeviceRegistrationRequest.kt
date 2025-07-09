package com.example.connect.domain.model.request

import com.example.connect.domain.model.PreKey

data class DeviceRegistrationRequest (
    val deviceId: String,
    val identityKey: String,
    val singedPreKey: String,
    val oneTimePreKeys: List<PreKey>,
)

