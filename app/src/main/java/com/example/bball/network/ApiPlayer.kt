package com.example.bball.network

import com.example.bball.models.Player
import com.example.bball.models.Stat
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(SERVEUR_API)
    .build()

interface ApiPlayer {
    @GET("joueurs/{id}")
    suspend fun getPlayerDetails(
        @Path("id") licence: String
    ): RequestResponse

    @GET("joueurs")
    suspend fun getAllPlayers() : PlayersResponse
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

data class PlayersResponse(
    val joueurs: List<Player>
)

