package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.ReviewDtoBuilder
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
        assertEquals(movie.backdropPath, movieDto.backdropPath)
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
        assertEquals(movies[1].backdropPath, moviesDto[1].backdropPath)
        assertEquals(movies[1].backdropPath, moviesDto[1].backdropPath)
        assertEquals(movies[0].releaseDate, moviesDto[0].releaseDate)
        assertEquals(movies[1].releaseDate, moviesDto[1].releaseDate)
        assertEquals(movies[0].title, moviesDto[0].title)
        assertEquals(movies[1].title, moviesDto[1].title)
        assert(movies[0].voteAverage == moviesDto[0].voteAverage)
        assert(movies[1].voteAverage == moviesDto[1].voteAverage)
        assertEquals(movies.size, moviesDto.size)
    }

    @Test
    fun `verify review dto to review model`() {
        val reviewDto = ReviewDtoBuilder().build()

        val review = reviewDto.toModel()

        assertEquals(review.avatarPath, reviewDto.authorDetailsDto?.avatarPath)
        assertEquals(review.username, reviewDto.authorDetailsDto?.username)
        assertEquals(review.content, reviewDto.content)
        assertEquals(review.id, reviewDto.id)
    }

    @Test
    fun `verify reviews list dto to reviews list model`() {
        val reviewsDto =
            listOf(
                ReviewDtoBuilder().withId("1").withContent("Content 1").build(),
                ReviewDtoBuilder().withId("2").withContent("Content 2").build()
            )

        val reviews = reviewsDto.toModel()

        assertEquals(reviews[0].id, reviewsDto[0].id)
        assertEquals(reviews[1].id, reviewsDto[1].id)
        assertEquals(reviews[0].content, reviewsDto[0].content)
        assertEquals(reviews[1].content, reviewsDto[1].content)
        assertEquals(reviews[0].avatarPath, reviewsDto[0].authorDetailsDto?.avatarPath)
        assertEquals(reviews[1].avatarPath, reviewsDto[1].authorDetailsDto?.avatarPath)
        assertEquals(reviews[1].username, reviewsDto[1].authorDetailsDto?.username)
        assertEquals(reviews[1].username, reviewsDto[1].authorDetailsDto?.username)
        assertEquals(reviews.size, reviewsDto.size)
    }
}