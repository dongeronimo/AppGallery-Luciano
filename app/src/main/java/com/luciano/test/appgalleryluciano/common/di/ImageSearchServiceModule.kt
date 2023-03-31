package com.luciano.test.appgalleryluciano.common.di

import com.luciano.test.appgalleryluciano.common.OkHttpClientProvider
import com.luciano.test.appgalleryluciano.datasource.ImageListDataSource
import com.luciano.test.appgalleryluciano.service.ImageSearchService
import com.luciano.test.appgalleryluciano.service.ImageSearchServiceMock
import com.luciano.test.appgalleryluciano.service.ImageSearchServiceReal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ImageSearchServiceModule {
    @Provides
    @Named("mockado")
    fun provideMock() : ImageSearchService {
        return ImageSearchServiceMock()
    }
    @Provides
    @Named("real")
    fun provideReal(listDataSource: ImageListDataSource): ImageSearchService {
        return ImageSearchServiceReal(listDataSource)
    }
}