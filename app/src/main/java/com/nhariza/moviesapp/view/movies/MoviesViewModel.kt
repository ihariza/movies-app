package com.nhariza.moviesapp.view.movies

import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    val moviesState: StateFlow<MoviesState>
        get() = _moviesState
    private val _moviesState = MutableStateFlow<MoviesState>(MoviesState.Loading)


    fun getMovies(language: String) {
        doInBackground {
            moviesRepository.getMovies(language)
                .catch {
                    _moviesState.value = MoviesState.Error(it)
                }
                .collect {
                    if (it.isNotEmpty()) {
                        _moviesState.value = MoviesState.Success(it)
                    } else {
                        _moviesState.value = MoviesState.Empty
                    }
                }
        }
    }
}