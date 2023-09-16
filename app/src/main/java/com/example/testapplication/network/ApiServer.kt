package com.example.testapplication.network

import retrofit2.http.GET

interface ApiServer {
    @GET("api/v0/stories/?format=json")
    suspend fun getCharacter(): NewsRequest
}