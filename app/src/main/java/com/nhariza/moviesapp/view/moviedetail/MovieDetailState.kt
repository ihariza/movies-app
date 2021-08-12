package com.nhariza.moviesapp.view.moviedetail

import com.nhariza.moviesapp.repository.model.Movie

sealed class MovieDetailState {
    data class Success(val movie: Movie) : MovieDetailState()
    object Empty : MovieDetailState()
}