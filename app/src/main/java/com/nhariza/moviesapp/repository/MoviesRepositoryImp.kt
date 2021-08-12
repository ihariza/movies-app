package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.repository.datasource.MoviesService
import com.nhariza.moviesapp.repository.exception.MoviesException
import com.nhariza.moviesapp.repository.exception.ReviewsException
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*


class MoviesRepositoryImp(
    private val moviesService: MoviesService
) : MoviesRepository {

    private var page = 1

    override fun getMovies(): Flow<List<Movie>> = flow {
        val response = moviesService.getPopularMovies(page, Locale.getDefault().toLanguageTag())

        when (response.statusCode) {
            null -> {
                response.results?.let {
                    incPage(response.page, response.totalPages)
                    emit(it.toModel())
                } ?: run {
                    emit(listOf<Movie>())
                }
            }
            else -> throw MoviesException(response.statusMessage)
        }
    }

    override fun getReviews(movieId: Int): Flow<List<Review>> = flow {
        val response = moviesService.getMovieReview(movieId)
        if (response.reviews.isNullOrEmpty()) {
            throw ReviewsException()
        } else {
            emit(response.reviews.toModel())
        }
    }

    private fun incPage(currentPage: Int?, totalPages: Int?) {
        currentPage?.let {
            totalPages?.let {
                if (currentPage < totalPages) {
                    page = currentPage.inc()
                }
            }
        }
    }

}