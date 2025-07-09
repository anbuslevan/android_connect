package com.example.connect.data.crypto

import android.util.Base64
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.spec.ECGenParameterSpec
import javax.inject.Inject
import javax.inject.Singleton
import com.example.connect.domain.model.PreKey
import java.security.PrivateKey

@Singleton
class PreKeyStore @Inject constructor(){
    private val EC_ALGORITHM = "EC"
    private val CURVE_NAME = "secp256r1"
    private val preKeys = mutableMapOf<Int, KeyPair>()

    private var singedPreKeyId: Int = -1
    private var singedPreKey: KeyPair? = null

    fun generatePreKey(preKeyId: Int): KeyPair {
        val keyGenerator = KeyPairGenerator.getInstance(EC_ALGORITHM)
        val ecSpec = ECGenParameterSpec(CURVE_NAME)
        keyGenerator.initialize(ecSpec, SecureRandom())
        val keyPair = keyGenerator.generateKeyPair()
        preKeys[preKeyId] = keyPair
        return keyPair
    }

    fun generateSignedPreKey(singedPreKeyId: Int): KeyPair{
        val keyGenerator = KeyPairGenerator.getInstance(EC_ALGORITHM)
        val ecSpec = ECGenParameterSpec(CURVE_NAME)
        keyGenerator.initialize(ecSpec, SecureRandom())
        val keyPair = keyGenerator.generateKeyPair()
        this.singedPreKeyId = singedPreKeyId
        this.singedPreKey = keyPair
        return keyPair
    }

    fun generateOneTimePreKey(startId: Int, count: Int): List<PreKey> {
        val keyGenerator = KeyPairGenerator.getInstance(EC_ALGORITHM)
        val ecSpec = ECGenParameterSpec(CURVE_NAME)
        keyGenerator.initialize(ecSpec, SecureRandom())

        val bundles = mutableListOf<PreKey>()

        for(i in 0 until count){
            val keyPair = keyGenerator.generateKeyPair()
            val preKeyId = startId + i
            preKeys[preKeyId] = keyPair
            val encoded = Base64.encodeToString(keyPair.public.encoded, Base64.NO_WRAP)
            bundles.add(PreKey(preKeyId, encoded))
        }
        return bundles
    }

    fun getAllPreKeyBundle(): List<PreKey> {
        return preKeys.map { (preKeyId, keyPair) ->
            PreKey(
                keyId = preKeyId,
                publicKey = Base64.encodeToString(keyPair.public.encoded, Base64.NO_WRAP)
            )
        }
    }

    fun getPreKey(preKeyId: Int): KeyPair? = preKeys[preKeyId]
    fun getSignedPreKeyId(): Int = singedPreKeyId
    fun getSignedPreKey(): KeyPair? = singedPreKey
    fun getSignedPreKeyPublic(): String = Base64.encodeToString(singedPreKey?.public?.encoded, Base64.NO_WRAP)
    fun getSignedPreKeyPrivate(): PrivateKey? = singedPreKey?.private

    fun clear(){
        preKeys.clear()
        singedPreKeyId = -1
        singedPreKey = null
    }
}