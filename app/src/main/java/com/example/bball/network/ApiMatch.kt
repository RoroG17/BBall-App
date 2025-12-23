package com.example.bball.network

import com.example.bball.models.Match
import com.example.bball.models.Stat
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiMatch {
    @GET("api/matchs")
    suspend fun getMatchs(): List<Match>

    @GET("api/matchs/{id}")
    suspend fun getDetailsMatch(
        @Path("id") id : Int
    ): DetailMatchResponse

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

data class DetailMatchResponse(
    val match : Match,
    val stats : List<Stat>
)
