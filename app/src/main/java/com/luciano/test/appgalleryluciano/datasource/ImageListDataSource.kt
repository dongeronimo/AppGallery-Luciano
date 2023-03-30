package com.luciano.test.appgalleryluciano.datasource

import android.util.Log
import com.luciano.test.appgalleryluciano.common.OkHttpClientProvider
import okhttp3.Request

import java.io.IOException
import javax.inject.Inject

class ImageListDataSource @Inject constructor(
    private val provider: OkHttpClientProvider,
    private val clientId: String
) {
    suspend fun getList(parameter:String){
        try {
            val request = Request.Builder()
                .url("https://api.imgur.com/3/gallery/search/?q=${parameter}")
                .header("Authorization", "Client-ID ${clientId}")
                .build()
            val response = provider.getClient().newCall(request).execute()
            response.body?.let {
                val result = it.string()
                Log.d("Geronimo", result)
            }
        }
        catch (err:Error){
            Log.e("Geronimo", "error while doing request: ${err.message?:"unknown err"}")
        }
    }
}