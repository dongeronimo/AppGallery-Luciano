package com.luciano.test.appgalleryluciano.common.di

import com.luciano.test.appgalleryluciano.common.OkHttpClientProvider
import com.luciano.test.appgalleryluciano.datasource.ImageListDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ImageListDataSourceModule {
    @Provides
    fun provide(provider: OkHttpClientProvider) = ImageListDataSource(provider, clientId = "dc784d23d3fecde")
}