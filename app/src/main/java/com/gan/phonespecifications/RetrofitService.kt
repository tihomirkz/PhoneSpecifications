package com.gan.phonespecifications

import com.gan.phonespecifications.data.Details
import com.gan.phonespecifications.data.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("v2/latest")
    fun getLatest() : Call<Result>

    @GET("v2/top-by-interest")
    fun getTopByInterest() : Call<Result>

    @GET("v2/top-by-fans")
    fun getTopByFans() : Call<Result>

    @GET("v2/search")
    fun getSearch(
        @Query("query") search: String,
    ) : Call<Result>

    @GET("/v2/{phone_slug}")
    fun getDetails(
        @Path("phone_slug") details: String,
    ) : Call<Details>

    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api-mobilespecs.azharimm.site/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}