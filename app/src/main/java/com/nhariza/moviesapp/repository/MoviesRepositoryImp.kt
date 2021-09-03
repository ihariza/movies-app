package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.repository.datasource.MoviesService
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.datasource.model.MoviesResponseDto
import com.nhariza.moviesapp.repository.exception.MoviesException
import com.nhariza.moviesapp.repository.exception.ReviewsException
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import java.util.*

@FlowPreview
class MoviesRepositoryImp(
    private val moviesService: MoviesService
) : MoviesRepository {

    override fun getMovies(page: Int): Flow<List<Movie>> = flow {
        emit(moviesService.getPopularMovies(page, Locale.getDefault().toLanguageTag()))
    }.flatMapConcat {
        onMoviesResponse(it)
    }

    override fun getReviews(movieId: Int): Flow<List<Review>> = flow {
        val response = moviesService.getMovieReview(movieId)

        when (response.statusCode) {
            null -> {
                if (response.results.isNullOrEmpty()) {
                    throw ReviewsException()
                } else {
                    emit(response.results.toModel())
                }
            }
            else -> throw ReviewsException()
        }
    }

    override fun searchMovie(query: String?, page: Int): Flow<List<Movie>> = flow {
        emit(moviesService.searchMovie(query, page, Locale.getDefault().toLanguageTag()))
    }.flatMapConcat {
        onMoviesResponse(it)
    }

    private fun onMoviesResponse(response: MoviesResponseDto<List<MovieDto>?>) = flow {
        when (response.statusCode) {
            null -> {
                response.results?.let {
                    emit(it.toModel())
                } ?: run {
                    emit(listOf<Movie>())
                }
            }
            else -> throw MoviesException(response.statusMessage)
        }
    }

}