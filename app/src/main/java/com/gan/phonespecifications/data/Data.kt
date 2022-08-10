package com.gan.phonespecifications.data

data class Data(
    val current_page: Int,
    val last_page: Int,
    val phones: List<Phone>,
    val title: String
)