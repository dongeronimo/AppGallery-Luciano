package com.luciano.test.appgalleryluciano.common.di

import android.content.Context
import com.luciano.test.appgalleryluciano.common.NoConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NoConnectionInterceptorModule {
    @Provides
    fun provide(@ApplicationContext appContext: Context):NoConnectionInterceptor {
        return NoConnectionInterceptor(appContext)
    }
}