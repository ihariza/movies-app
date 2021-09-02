package com.nhariza.moviesapp.view.movies

import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    companion object {
        private const val PAGE_LIMIT = 20
        private const val PAGE_THRESHOLD = 5
    }

    private var viewThreshold = PAGE_LIMIT - PAGE_THRESHOLD
    private val movies = mutableListOf<Movie>()


    val moviesState: StateFlow<MoviesState>
        get() = _moviesState
    private val _moviesState = MutableStateFlow<MoviesState>(MoviesState.Loading)

    init {
        getMovies()
    }

    fun getMovies() {
        doInBackground {
            moviesRepository.getMovies()
                .catch {
                    _moviesState.value = MoviesState.Error {
                        viewThreshold = movies.size - PAGE_THRESHOLD
                        _moviesState.value = MoviesState.Success(movies.toList())
                        if (movies.size == 0) {
                            getMovies()
                        }
                    }
                }
                .collect {
                    movies.addAll(it)
                    _moviesState.value = MoviesState.Success(movies.toList())
                }
        }
    }

    fun checkRequireNewPage(lastVisible: Int) {
        if (lastVisible >= viewThreshold) {
            viewThreshold = movies.size + PAGE_LIMIT - PAGE_THRESHOLD
            getMovies()
        }
    }
}