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

    companion object {
        private const val PAGE_THRESHOLD = 20
    }

    private var viewThreshold = 15

    val moviesState: StateFlow<MoviesState>
        get() = _moviesState
    private val _moviesState = MutableStateFlow<MoviesState>(MoviesState.Loading)


    fun getMovies() {
        doInBackground {
            moviesRepository.getMovies()
                .catch {
                    _moviesState.value = MoviesState.Error(it)
                }
                .collect {
                    _moviesState.value = MoviesState.Success(it)
                }
        }
    }

    fun checkRequireNewPage(lastVisible: Int) {
        if (lastVisible >= viewThreshold) {
            viewThreshold += PAGE_THRESHOLD
            getMovies()
        }
    }
}