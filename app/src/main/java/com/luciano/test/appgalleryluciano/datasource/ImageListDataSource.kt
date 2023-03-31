package com.luciano.test.appgalleryluciano.datasource

import android.util.Log
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.luciano.test.appgalleryluciano.common.OkHttpClientProvider
import com.luciano.test.appgalleryluciano.entity.ImgurImage
import okhttp3.Request

import java.io.IOException

import javax.inject.Inject

class ImageListDataSource @Inject constructor(
    private val provider: OkHttpClientProvider,
    private val clientId: String
) {
    private fun isJpeg(value:String) = value.contains("jpeg")||value.contains("jpg")
    suspend fun getList(parameter:String): List<ImgurImage> {
        try {
            val request = Request.Builder()
                .url("https://api.imgur.com/3/gallery/search/?q=${parameter}")
                .header("Authorization", "Client-ID ${clientId}")
                .build()
            val response = provider.getClient().newCall(request).execute()
            val images = response.body?.let {
                val result = it.string()
                val objectMapper = ObjectMapper()
                val dataNodes: List<JsonNode> = objectMapper.readTree(result).path("data").toList()
                dataNodes.map { dn->
                    val title = dn.findValue("title").textValue()
                    val favoriteCoubt = dn.findValue("favorite_count").intValue()
                    val imagesNodes = dn.path("images").toList()
                    imagesNodes
                        .filter { imageNode-> imageNode.findValue("type").textValue().contains("jpeg")||
                                imageNode.findValue("type").textValue().contains("jpg")}
                        .map { imageNode->
                            val desc = if(imageNode.hasNonNull("description"))
                                imageNode.findValue("description").textValue()
                            else
                                ""
                            val url = imageNode.findValue("link").textValue()
                            ImgurImage(exibitionName = title, followers = favoriteCoubt,
                                description = desc, url = url)
                    }
                }.flatten()
            }?:run{
                Log.w("Geronimo", "empty image list for search $parameter")
                emptyList()
            }
            return images
        }

        catch (err:IOException){

            Log.e("Geronimo", "error while doing request: ${err.message?:"unknown err"}")
            throw err
        }
    }
}