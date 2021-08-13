package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    fun getMovies(): Flow<List<Movie>>

    fun getReviews(movieId: Int): Flow<List<Review>>
}