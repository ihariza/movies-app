package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.repository.datasource.MoviesService
import com.nhariza.moviesapp.repository.exception.MoviesException
import com.nhariza.moviesapp.repository.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class MoviesRepositoryImp(
    private val moviesService: MoviesService
) : MoviesRepository {

    private var page = 1

    override fun getMovies(language: String): Flow<List<Movie>> = flow {
        val response = moviesService.getPopularMovies(page, language)

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