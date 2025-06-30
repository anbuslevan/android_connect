package com.example.connect.di

import com.example.connect.network.APIClient
import com.example.connect.network.APIService
import com.example.connect.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideApiService(): APIService = APIClient.create()

    @Provides
    fun provideAuthRepository(apiService: APIService) = AuthRepository(apiService)
}