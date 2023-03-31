package com.luciano.test.appgalleryluciano.common.di


import com.luciano.test.appgalleryluciano.common.NoConnectionInterceptor
import com.luciano.test.appgalleryluciano.common.OkHttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientProviderModule {
    @Provides

    fun provideReal(noConnectionInterceptor: NoConnectionInterceptor):
            OkHttpClientProvider = OkHttpClientProvider(noConnectionInterceptor)

}