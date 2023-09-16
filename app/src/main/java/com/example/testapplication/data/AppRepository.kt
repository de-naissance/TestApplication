package com.example.testapplication.data

import com.example.testapplication.network.ApiServer
import com.example.testapplication.network.NewsRequest

interface AppRepository {
    suspend fun getNews(): NewsRequest
}

class NetworkRepository(
    private val appApiRepository: ApiServer
): AppRepository {
    override suspend fun getNews(): NewsRequest {
        return appApiRepository.getCharacter()
    }
}