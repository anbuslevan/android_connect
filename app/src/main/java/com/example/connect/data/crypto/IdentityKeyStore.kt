package com.example.connect.data.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.spec.ECGenParameterSpec
import javax.inject.Inject

class IdentityKeyStore @Inject constructor(){
    private val keyAlias = "identity_key"
    private val androidKeyStore = "AndroidKeyStore"

    fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_EC, androidKeyStore)

        val parameterSpec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        ).apply {
            setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
            setDigests(KeyProperties.DIGEST_SHA256)
            setUserAuthenticationRequired(false)
        }.build()

        keyPairGenerator.initialize(parameterSpec)
        return keyPairGenerator.generateKeyPair()
    }

    fun getKeyPair(): KeyPair? {
        val keyStore = KeyStore.getInstance(androidKeyStore).apply { load(null) }
        val privateKey = keyStore.getKey(keyAlias, null) as? PrivateKey ?: return null
        val publicKey = keyStore.getCertificate(keyAlias)?.publicKey ?: return null
        return KeyPair(publicKey, privateKey)
    }

    fun ensureKeyPair(): KeyPair = getKeyPair() ?: generateKeyPair()

    fun getEncodedPublicKeyBase64(): String {
        val publicKey = ensureKeyPair().public
        return Base64.encodeToString(publicKey.encoded, Base64.NO_WRAP)
    }
}