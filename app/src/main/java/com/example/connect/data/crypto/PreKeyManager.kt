package com.example.connect.data.crypto

import android.security.keystore.KeyProperties
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.ECGenParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreKeyManager @Inject constructor() {
    fun generateSignedPreKey(identityPrivateKey: PrivateKey): SingedPreKey {
        val keyPair = generateKeyPair()
        val signature = sign(identityPrivateKey, keyPair.public.encoded)
        return SingedPreKey(keyPair, signature)
    }

    fun generateOneTimePreKeys(count: Int = 20): List<KeyPair> = List(count) { generateKeyPair() }

    private fun generateKeyPair(): KeyPair {
        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC).apply {
            initialize(ECGenParameterSpec("secp256r1"))
            return generateKeyPair()
        }
    }

    private fun sign(privateKey: PrivateKey, data: ByteArray): ByteArray {
        Signature.getInstance("SHA256withECDSA").apply {
            initSign(privateKey)
            update(data)
            return sign()
        }
    }

    data class SingedPreKey(val keyPair: KeyPair, val signature: ByteArray)
}