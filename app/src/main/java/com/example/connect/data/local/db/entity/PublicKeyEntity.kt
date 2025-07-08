package com.example.connect.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "public_keys")
data class PublicKeyEntity(
    @PrimaryKey val id: String,
    val publicKey: String
)

