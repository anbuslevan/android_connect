package com.example.connect.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.connect.data.local.db.entity.PublicKeyEntity

@Dao
interface PublicKeyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPublicKey(publicKeyEntity: PublicKeyEntity)

    @Query("SELECT * FROM public_keys WHERE id = :id")
    suspend fun getPublicKeyById(id: String): PublicKeyEntity?

    @Query("DELETE FROM public_keys WHERE id = :id")
    suspend fun deletePublicKeyById(id: String)
}