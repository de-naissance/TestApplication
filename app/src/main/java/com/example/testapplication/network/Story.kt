package com.example.testapplication.network

data class Story(
    val date: Int,
    val description: String,
    val exp_date: Int,
    val image_logo: String,
    val is_add: Boolean,
    val material_url: String,
    val news_name: String,
    val pages: List<Page>,
    val text_url: String,
    val unique_name: String,
    val url: String
)