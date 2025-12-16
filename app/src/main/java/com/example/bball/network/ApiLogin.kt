package com.example.bball.network

import com.example.bball.models.Match
import com.example.bball.models.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL =
    "https://statmatch.alwaysdata.net/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiLogin {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("connect")
    suspend fun connect(@Body request: LoginRequest): Response<LoginResponse>

    @POST("update")
    suspend fun initAccount(@Body request: LoginRequest): Response<LoginResponse>
}

object LoginAPI {
    val retrofitService: ApiLogin by lazy {
        retrofit.create(ApiLogin::class.java)
    }
}

data class LoginRequest(
    val username: String,
    val password: String? = null
)

data class LoginResponse(
    val user : User,
    val message: String
)