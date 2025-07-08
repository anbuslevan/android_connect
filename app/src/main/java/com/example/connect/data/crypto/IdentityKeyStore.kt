package com.example.connect.data.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.SecureRandom
import java.security.spec.ECGenParameterSpec
import javax.inject.Inject
import java.security.cert.X509Certificate
import java.util.Calendar
import java.util.Date
import javax.security.auth.x500.X500Principal

class IdentityKeyStore @Inject constructor(){
    private val identityIdAlias = "identity_id"
    private val privateKeyAlias = "private_key"
    private val androidKeyStore = "AndroidKeyStore"
    private val EC_CURVE_NAME = "secp256r1"

    fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_EC, androidKeyStore)

        val parameterSpec = KeyGenParameterSpec.Builder(
            privateKeyAlias,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        ).apply {
            setAlgorithmParameterSpec(ECGenParameterSpec(EC_CURVE_NAME))
            setDigests(KeyProperties.DIGEST_SHA256)
            setUserAuthenticationRequired(false)
        }.build()

        keyPairGenerator.initialize(parameterSpec)
        return keyPairGenerator.generateKeyPair()
    }

    fun getKeyPair(): KeyPair? {
        val keyStore = KeyStore.getInstance(androidKeyStore).apply { load(null) }
        val privateKey = keyStore.getKey(privateKeyAlias, null) as? PrivateKey ?: return null
        val publicKey = keyStore.getCertificate(privateKeyAlias)?.publicKey ?: return null
        return KeyPair(publicKey, privateKey)
    }

    fun ensureKeyPair(): KeyPair = getKeyPair() ?: generateKeyPair()

    fun getEncodedPublicKeyBase64(): String {
        val publicKey = ensureKeyPair().public
        return Base64.encodeToString(publicKey.encoded, Base64.NO_WRAP)
    }

    fun generateDeviceId(): String {
        val keyStore = KeyStore.getInstance(androidKeyStore).apply { load(null) }

        if(keyStore.containsAlias(identityIdAlias)) {
            val certificate = keyStore.getCertificate(identityIdAlias) as X509Certificate
            val subject = certificate.subjectX500Principal.name
            return subject.substringAfter("CN=")
        }

        val deviceId = generateRandomId()


        val spec = KeyGenParameterSpec.Builder(
            identityIdAlias,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        ).apply {
            setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
            setDigests(KeyProperties.DIGEST_SHA256)
            setCertificateSubject(X500Principal("CN=$deviceId"))
            setCertificateSerialNumber(BigInteger.ONE)
            setCertificateNotBefore(Date())
            setCertificateNotAfter(Calendar.getInstance().apply {
                add(Calendar.YEAR, 30)
            }.time)
            setUserAuthenticationRequired(false)
        }.build()

        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, androidKeyStore)
            .apply {
                initialize(spec)
                generateKeyPair()
            }

        return deviceId
    }

    private fun generateRandomId(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }
}