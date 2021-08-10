package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.model.Movie


fun MovieDto.toModel(): Movie = Movie(
    id,
    title,
    posterPath,
    overview,
    releaseDate,
    voteAverage
)

fun List<MovieDto>.toModel(): List<Movie> = map { it.toModel() }