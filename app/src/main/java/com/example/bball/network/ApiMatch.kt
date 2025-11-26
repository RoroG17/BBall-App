package com.example.bball.network

import com.example.bball.models.Match
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory



private const val BASE_URL =
    "https://statmatch.alwaysdata.net/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiMatch {
    @GET("matchs")
    suspend fun getMatchs(): List<Match>
}

object MatchApi {
    val retrofitService: ApiMatch by lazy {
        retrofit.create(ApiMatch::class.java)
    }
}
