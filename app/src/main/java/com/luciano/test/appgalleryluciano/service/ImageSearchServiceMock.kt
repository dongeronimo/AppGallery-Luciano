package com.luciano.test.appgalleryluciano.service

import com.luciano.test.appgalleryluciano.entity.ImgurImage

class ImageSearchServiceMock : ImageSearchService {
    override suspend fun search(value: String): List<ImgurImage> {
        return emptyList()
    }
}