package com.example.bball.network

import com.example.bball.models.Season
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory



private const val BASE_URL =
    "https://statmatch.alwaysdata.net/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiSeason {
    @GET("saisons")
    suspend fun getSeasons(): List<Season>
}

object SeasonApi {
    val retrofitService: ApiSeason by lazy {
        retrofit.create(ApiSeason::class.java)
    }
}
