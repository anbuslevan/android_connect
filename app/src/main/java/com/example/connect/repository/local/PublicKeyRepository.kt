package com.example.connect.repository.local

import com.example.connect.data.local.db.dao.PublicKeyDAO
import com.example.connect.data.local.db.entity.PublicKeyEntity
import com.example.connect.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PublicKeyRepository @Inject constructor(
    private val publicKeyDao: PublicKeyDAO,
    private val authRepository: AuthRepository
) {
    suspend fun insertPublicKey(id: String, publicKey: String) = withContext(Dispatchers.IO) {
        publicKeyDao.insertPublicKey(PublicKeyEntity(id = id, publicKey = publicKey))
    }

    suspend fun getPublicKey(id: String): PublicKeyEntity? = withContext(Dispatchers.IO) {
        publicKeyDao.getPublicKeyById(id)
    }

    suspend fun deletePublicKey(id: String) = withContext(Dispatchers.IO) {
        publicKeyDao.deletePublicKeyById(id)
    }

    suspend fun hasPublicKey(id: String): Boolean = withContext(Dispatchers.IO) {
        publicKeyDao.getPublicKeyById(id) != null
    }

    suspend fun fetchPublicKey(id: String) = authRepository.getPublicKey()
}