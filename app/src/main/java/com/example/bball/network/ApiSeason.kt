package com.example.bball.network

import com.example.bball.models.Season
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiSeason {
    @GET("saisons/{licence}")
    suspend fun getSeasons(
        @Path("licence") licence : String
    ): List<Season>
}

object SeasonApi {
    val retrofitService: ApiSeason by lazy {
        retrofit.create(ApiSeason::class.java)
    }
}
