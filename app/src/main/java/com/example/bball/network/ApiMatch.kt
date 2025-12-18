package com.example.bball.network

import com.example.bball.models.Match
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiMatch {
    @GET("api/matchs")
    suspend fun getMatchs(): List<Match>

    @GET("accueil")
    suspend fun getAccueil(): AccueilResponse
}

object MatchApi {
    val retrofitService: ApiMatch by lazy {
        retrofit.create(ApiMatch::class.java)
    }
}

data class AccueilResponse(
    val previousGame : Match,
    val nextGame : Match
)
