package com.example.bball.network

import com.example.bball.models.Player
import com.example.bball.models.Stat
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


private const val BASE_URL =
    "https://statmatch.alwaysdata.net/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiPlayer {
    @GET("api/joueurs/{id}")
    suspend fun getPlayerDetails(
        @Path("id") licence: String
    ): RequestResponse
}

object PlayerApi {
    val retrofitService: ApiPlayer by lazy {
        retrofit.create(ApiPlayer::class.java)
    }
}

data class RequestResponse(
    val joueur : Player,
    val stats : List<Stat>
)
