package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.ResponseDtoBuilder
import com.nhariza.moviesapp.repository.datasource.MoviesService
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.exception.MoviesException
import com.nhariza.moviesapp.repository.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    companion object {
        private const val PAGE = 1
        private const val LANGUAGE = "en-EN"
    }

    @RelaxedMockK
    lateinit var moviesService: MoviesService

    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        moviesRepository = MoviesRepositoryImp(moviesService)
    }

    @Test
    fun `getMovies should return a list of movies`() = runBlockingTest {
        val responseDtoBuilder =
            ResponseDtoBuilder<List<MovieDto>>().withResults(listOf(MovieDtoBuilder().build()))

        verifyGetMoviesWith(responseDtoBuilder, responseDtoBuilder.build().results?.toModel())
    }

    @Test
    fun `given an empty list, getMovies should return an empty list`() = runBlockingTest {
        val responseDtoBuilder = ResponseDtoBuilder<List<MovieDto>>()
            .withResults(listOf<MovieDto>())

        verifyGetMoviesWith(responseDtoBuilder, responseDtoBuilder.build().results?.toModel())
    }

    @Test
    fun `given a null list, getMovies should return a empty list`() = runBlockingTest {
        val responseDtoBuilder = ResponseDtoBuilder<List<MovieDto>>()
            .withResults(null)

        verifyGetMoviesWith(responseDtoBuilder, listOf())
    }

    @Test
    fun `given response failure, getMovies should return a MoviesException with a message`() =
        runBlockingTest {
            val messageError = "Movies message error"
            val responseDtoBuilder = ResponseDtoBuilder<List<MovieDto>>()
                .statusMessage(messageError).withStatusCode(401)

            coEvery {
                moviesService.getPopularMovies(PAGE, LANGUAGE)
            } returns responseDtoBuilder.build()

            val getMoviesFlow = moviesRepository.getMovies(PAGE, LANGUAGE)

            getMoviesFlow
                .catch {
                    assertEquals(it.message, messageError)
                    assertTrue(it is MoviesException)
                }
                .collect()
        }

    @Test
    fun `given an exception, getMovies should return the exception`() =
        runBlockingTest {
            coEvery {
                moviesService.getPopularMovies(PAGE, LANGUAGE)
            } throws ClassCastException()

            val getMoviesFlow = moviesRepository.getMovies(PAGE, LANGUAGE)

            getMoviesFlow
                .catch { assertTrue(it is ClassCastException) }
                .collect()
        }

    private suspend fun verifyGetMoviesWith(
        responseDtoBuilder: ResponseDtoBuilder<List<MovieDto>>,
        expected: List<Movie>?
    ) {
        coEvery {
            moviesService.getPopularMovies(PAGE, LANGUAGE)
        } returns responseDtoBuilder.build()

        val getMoviesFlow = moviesRepository.getMovies(PAGE, LANGUAGE)

        getMoviesFlow.collect {
            coVerify(exactly = 1) { moviesService.getPopularMovies(PAGE, LANGUAGE) }
            assertEquals(expected, it)
        }
    }
}