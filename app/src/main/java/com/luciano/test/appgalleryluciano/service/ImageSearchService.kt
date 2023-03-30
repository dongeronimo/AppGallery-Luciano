package com.luciano.test.appgalleryluciano.service

interface ImageSearchService {
    abstract suspend fun search(value: String)
}