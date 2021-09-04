package com.nhariza.moviesapp.view.movies

import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.base.BaseViewModel
import com.nhariza.moviesapp.view.common.Pager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MoviesViewModel(
    private val moviesRepository: MoviesRepository,
    private val pager: Pager
) : BaseViewModel() {

    private var query: String? = ""
    private val movies = mutableListOf<Movie>()

    val moviesState: StateFlow<MoviesState>
        get() = _moviesState
    private val _moviesState = MutableStateFlow<MoviesState>(MoviesState.Loading)

    init {
        getMovies()
    }

    fun getMovies() {
        doInBackground {
            moviesRepository.getMovies(pager.page)
                .catch {
                    setMovieStateError()
                }
                .collect {
                    pager.setPageLoaded(it.size)
                    postMovies(it)
                }
        }
    }

    fun queryRequest(newQuery: String?) {
        if (query == newQuery) {
            return
        }
        query = newQuery
        movies.clear()
        pager.reset()
        if (query.isNullOrBlank()) {
            getMovies()
        } else {
            searchMovie()
        }
    }

    fun checkRequireNewPage(lastVisible: Int) {
        if (pager.requireNewPage(lastVisible, movies.size)) {
            val currentQuery = query
            if (!currentQuery.isNullOrBlank()) {
                searchMovie()
            } else {
                getMovies()
            }
        }
    }

    private fun searchMovie() {
        doInBackground {
            moviesRepository.searchMovie(query, pager.page)
                .catch {
                    setMovieStateError()
                }
                .collect {
                    pager.setPageLoaded(it.size)
                    postMovies(it)
                }
        }
    }

    private fun postMovies(newMovies: List<Movie>) {
        movies.addAll(newMovies)
        _moviesState.value = MoviesState.Success(movies.toList())
    }

    private fun setMovieStateError() {
        _moviesState.value = MoviesState.Error {
            pager.setPageError()
            _moviesState.value = MoviesState.Success(movies.toList())
            if (movies.size == 0) {
                getMovies()
            }
        }
    }
}