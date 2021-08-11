package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.repository.model.Movie
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    fun getMovies(): Flow<List<Movie>>

}