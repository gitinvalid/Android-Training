package com.example.m06.module

data class PopularMovie(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Double
)

data class PopularMovies(
    val results: List<PopularMovie>
)