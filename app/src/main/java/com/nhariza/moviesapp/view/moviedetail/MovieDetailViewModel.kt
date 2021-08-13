package com.nhariza.moviesapp.view.moviedetail

import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review
import com.nhariza.moviesapp.view.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MovieDetailViewModel(private val moviesRepository: MoviesRepository) : BaseViewModel() {

    private var movie: Movie? = null
    private var reviews = mutableListOf<Review>()

    val movieDetailState: StateFlow<MovieDetailState>
        get() = _movieDetailState
    private val _movieDetailState = MutableStateFlow<MovieDetailState>(MovieDetailState.Empty)

    fun loadMovie(movie: Movie) {
        this.movie = movie
        _movieDetailState.value = MovieDetailState.SuccessMovie(movie)
        getReviews()
    }

    private fun getReviews() {
        movie?.id?.let {
            doInBackground {
                moviesRepository.getReviews(it)
                    .catch {
                        _movieDetailState.value = MovieDetailState.ErrorReviews
                    }
                    .collect {
                        reviews.addAll(it)
                        _movieDetailState.value = MovieDetailState.SuccessReviews(reviews.toList())
                    }
            }
        }
    }
}