package com.nhariza.moviesapp.repository.datasource

import com.nhariza.moviesapp.repository.datasource.model.ResponseDto
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET(PathService.GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): ResponseDto<List<MovieDto>?>
}