package com.luciano.test.appgalleryluciano.common.di

import com.luciano.test.appgalleryluciano.common.OkHttpClientProvider
import com.luciano.test.appgalleryluciano.datasource.ImageSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ImageSourceModule {
    @Provides
    fun provide(provider:OkHttpClientProvider) =
        ImageSource(provider)
}