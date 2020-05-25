package com.example.m09

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("advice")
    fun getAdvice(): Call<Advice>
}

