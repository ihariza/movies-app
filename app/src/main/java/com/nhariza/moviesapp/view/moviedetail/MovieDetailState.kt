package com.nhariza.moviesapp.view.moviedetail

import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review

sealed class MovieDetailState {
    data class SuccessMovie(val movie: Movie) : MovieDetailState()
    data class SuccessReviews(val reviews: List<Review>) : MovieDetailState()
    object ErrorReviews : MovieDetailState()
    object Empty : MovieDetailState()
}