package com.gan.phonespecifications

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getLatest() = retrofitService.getLatest()
    fun getTopByInterest() = retrofitService.getTopByInterest()
    fun getTopByFans() = retrofitService.getTopByFans()
    fun getSearch(search: String) = retrofitService.getSearch(search)
    fun getDetails(details: String) = retrofitService.getDetails(details)
}