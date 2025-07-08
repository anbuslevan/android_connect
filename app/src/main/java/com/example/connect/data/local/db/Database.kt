package com.example.connect.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.connect.data.local.db.dao.PublicKeyDAO
import com.example.connect.data.local.db.entity.PublicKeyEntity

@Database(entities = [PublicKeyEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun publicKeyDao(): PublicKeyDAO
}