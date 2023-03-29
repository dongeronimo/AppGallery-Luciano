package com.luciano.test.appgalleryluciano.common.di

import com.luciano.test.appgalleryluciano.service.ImageSearchService
import com.luciano.test.appgalleryluciano.service.ImageSearchServiceMock
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
}