package com.nhariza.moviesapp.repository.datasource

import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.datasource.model.MoviesResponseDto
import com.nhariza.moviesapp.repository.datasource.model.ReviewResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(PathService.GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): MoviesResponseDto<List<MovieDto>?>

    @GET(PathService.GET_MOVIE_REVIEW)
    suspend fun getMovieReview(
        @Path("movie_id") movieId: Int
    ): ReviewResponseDto
}