package com.example.connect.di

import com.example.connect.data.crypto.IdentityKeyStore
import com.example.connect.data.crypto.PreKeyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CryptoModule {
    @Provides
    @Singleton
    fun provideIdentityKeyStore(): IdentityKeyStore = IdentityKeyStore()

    @Provides
    @Singleton
    fun providePreKeyManager(): PreKeyManager = PreKeyManager()
}