package com.nhariza.moviesapp.view.moviedetail

import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieDetailViewModel : BaseViewModel() {

    val movieDetailState: StateFlow<MovieDetailState>
        get() = _movieDetailState
    private val _movieDetailState = MutableStateFlow<MovieDetailState>(MovieDetailState.Empty)

    fun loadMovie(movie: Movie) {
        _movieDetailState.value = MovieDetailState.Success(movie)
    }
}