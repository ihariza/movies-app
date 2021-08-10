package com.nhariza.moviesapp.builder.model

import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MoviesListFlowBuilder {

    private var moviesListDto: List<MovieDto> = listOf(MovieDtoBuilder().build())

    fun withMoviesListDto(moviesListDto: List<MovieDto>): MoviesListFlowBuilder {
        this.moviesListDto = moviesListDto
        return this
    }

    fun build(): Flow<List<Movie>> = flowOf(moviesListDto.toModel())
}