package com.example.connect.data.crypto

import com.example.connect.repository.local.PublicKeyRepository
import com.example.connect.utils.Constant
import javax.inject.Inject

class PublicKeyStore @Inject constructor(
    private val serverPublicKeyStore: ServerPublicKeyStore,
    private val publicKeyRepository: PublicKeyRepository
) {
    suspend fun fetchPublicKey(id: String = Constant.ServerKeyId){
        val publicKey = publicKeyRepository.getPublicKey(id)

        if(publicKey == null) {
            serverPublicKeyStore.fetchAndStorePublicKeyServer()
        }
    }
}