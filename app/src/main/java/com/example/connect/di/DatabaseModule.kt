package com.example.connect.di

import android.content.Context
import androidx.room.Room
import com.example.connect.data.local.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "connect"
        ).build()
    }

    @Provides
    fun providePublicKeyDao(database: Database) = database.publicKeyDao()
}