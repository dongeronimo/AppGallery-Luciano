package com.luciano.test.appgalleryluciano.common


import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpClientProvider @Inject constructor() {
    private val client: OkHttpClient = OkHttpClient()
    fun getClient():OkHttpClient {
        return client
    }
}