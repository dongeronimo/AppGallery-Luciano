package com.luciano.test.appgalleryluciano.common


import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpClientProvider @Inject constructor(
    private val noConnectionInterceptor: NoConnectionInterceptor
) {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(noConnectionInterceptor).build()

    fun getClient():OkHttpClient {

        return client
    }
}