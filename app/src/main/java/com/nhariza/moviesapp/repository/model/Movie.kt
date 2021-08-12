package com.nhariza.moviesapp.repository.model

import java.io.Serializable


data class Movie(
    val id: Int?,
    val title: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
) : Serializable