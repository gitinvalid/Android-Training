package com.example.m06.service

import com.example.m06.module.PopularMovies
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular")
    fun getMovie(@Query("api_key") apiKey: String): Call<PopularMovies>
}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
