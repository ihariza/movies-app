package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.datasource.model.ReviewDto
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review


fun MovieDto.toModel(): Movie = Movie(
    id,
    title,
    posterPath,
    backdropPath,
    overview,
    releaseDate,
    voteAverage
)

@JvmName("toModelMovieDto")
fun List<MovieDto>.toModel(): List<Movie> = map { it.toModel() }

fun ReviewDto.toModel(): Review = Review(
    id,
    authorDetailsDto?.username,
    authorDetailsDto?.avatarPath,
    content
)

@JvmName("toModelReviewDto")
fun List<ReviewDto>.toModel(): List<Review> = map { it.toModel() }