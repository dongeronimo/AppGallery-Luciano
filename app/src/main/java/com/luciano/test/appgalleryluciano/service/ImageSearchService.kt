package com.luciano.test.appgalleryluciano.service

import com.luciano.test.appgalleryluciano.entity.ImgurImage

interface ImageSearchService {
    abstract suspend fun search(value: String):List<ImgurImage>
}