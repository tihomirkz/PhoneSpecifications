package com.gan.phonespecifications.data

data class DetailData(
    val brand: String,
    val dimension: String,
    val os: String,
    val phone_images: List<String>,
    val phone_name: String,
    val release_date: String,
    val storage: String,
    val thumbnail: String
)