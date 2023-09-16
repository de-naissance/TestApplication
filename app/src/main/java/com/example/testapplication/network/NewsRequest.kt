package com.example.testapplication.network

data class NewsRequest(
    val detail: Detail,
    val error: String,
    val status: String,
    val status_id: Int
)