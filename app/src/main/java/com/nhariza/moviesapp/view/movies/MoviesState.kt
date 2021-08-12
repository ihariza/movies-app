package com.nhariza.moviesapp.view.movies

import com.nhariza.moviesapp.repository.model.Movie

sealed class MoviesState {
    data class Success(val movies: List<Movie>) : MoviesState()
    class Error(val action: () -> Unit) : MoviesState()
    object Loading : MoviesState()
}