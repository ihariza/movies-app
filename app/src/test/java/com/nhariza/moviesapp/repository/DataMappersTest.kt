package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMappersTest {

    @Test
    fun `verify movie dto to movie model`() {
        val movieDto = MovieDtoBuilder().build()

        val movie = movieDto.toModel()

        assertEquals(movie.id, movieDto.id)
        assertEquals(movie.overview, movieDto.overview)
        assertEquals(movie.posterPath, movieDto.posterPath)
        assertEquals(movie.releaseDate, movieDto.releaseDate)
        assertEquals(movie.title, movieDto.title)
        assert(movie.voteAverage == movieDto.voteAverage)
    }

    @Test
    fun `verify movies list dto to movies list model`() {
        val moviesDto =
            listOf(
                MovieDtoBuilder().withId(1).withTitle("Title 1").build(),
                MovieDtoBuilder().withId(2).withTitle("Title 2").build()
            )

        val movies = moviesDto.toModel()

        assertEquals(movies[0].id, moviesDto[0].id)
        assertEquals(movies[1].id, moviesDto[1].id)
        assertEquals(movies[0].overview, moviesDto[0].overview)
        assertEquals(movies[1].overview, moviesDto[1].overview)
        assertEquals(movies[0].posterPath, moviesDto[0].posterPath)
        assertEquals(movies[1].posterPath, moviesDto[1].posterPath)
        assertEquals(movies[0].releaseDate, moviesDto[0].releaseDate)
        assertEquals(movies[1].releaseDate, moviesDto[1].releaseDate)
        assertEquals(movies[0].title, moviesDto[0].title)
        assertEquals(movies[1].title, moviesDto[1].title)
        assert(movies[0].voteAverage == moviesDto[0].voteAverage)
        assert(movies[1].voteAverage == moviesDto[1].voteAverage)
        assertEquals(movies.size, moviesDto.size)
    }
}