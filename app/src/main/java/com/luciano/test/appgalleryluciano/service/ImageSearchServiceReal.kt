package com.luciano.test.appgalleryluciano.service

import android.util.Log
import com.luciano.test.appgalleryluciano.datasource.ImageListDataSource

import javax.inject.Inject


class ImageSearchServiceReal @Inject constructor(
    private val imageListSource: ImageListDataSource
): ImageSearchService {
    override suspend fun search(value: String) {
        Log.d("Geronimo", "aaa")
        imageListSource.getList(value)
        Log.d("Geronimo", "bbb")
    }

}