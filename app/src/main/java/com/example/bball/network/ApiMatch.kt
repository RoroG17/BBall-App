package com.example.bball.network

import com.example.bball.models.Match
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlinx.serialization.json.Json



private const val BASE_URL =
    "https://statmatch.alwaysdata.net/api/"


// Configuration du JSON (ignore les champs inconnus pour éviter les crashs)
private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
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
