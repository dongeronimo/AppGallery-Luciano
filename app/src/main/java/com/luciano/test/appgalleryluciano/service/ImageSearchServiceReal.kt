package com.luciano.test.appgalleryluciano.service

import android.util.Log
import com.luciano.test.appgalleryluciano.datasource.ImageListDataSource
import com.luciano.test.appgalleryluciano.entity.ImgurImage

import javax.inject.Inject


class ImageSearchServiceReal @Inject constructor(
    private val imageListSource: ImageListDataSource
): ImageSearchService {
    override suspend fun search(value: String): List<ImgurImage> {
        return imageListSource.getList(value)
    }

}