package com.nhariza.moviesapp.repository

import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.MoviesResponseDtoBuilder
import com.nhariza.moviesapp.builder.dto.ReviewDtoBuilder
import com.nhariza.moviesapp.builder.dto.ReviewsResponseDtoBuilder
import com.nhariza.moviesapp.repository.datasource.MoviesService
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.exception.MoviesException
import com.nhariza.moviesapp.repository.exception.ReviewsException
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    companion object {
        private const val PAGE = 1
        private const val MOVIE_ID = 834232
        private val LANGUAGE = Locale.getDefault().toLanguageTag()
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
            MoviesResponseDtoBuilder<List<MovieDto>>().withResults(listOf(MovieDtoBuilder().build()))

        verifyGetMoviesWith(responseDtoBuilder, responseDtoBuilder.build().results?.toModel())
    }

    @Test
    fun `given an empty list, getMovies should return an empty list`() = runBlockingTest {
        val responseDtoBuilder = MoviesResponseDtoBuilder<List<MovieDto>>()
            .withResults(listOf())

        verifyGetMoviesWith(responseDtoBuilder, responseDtoBuilder.build().results?.toModel())
    }

    @Test
    fun `given a null list, getMovies should return a empty list`() = runBlockingTest {
        val responseDtoBuilder = MoviesResponseDtoBuilder<List<MovieDto>>()
            .withResults(null)

        verifyGetMoviesWith(responseDtoBuilder, listOf())
    }

    @Test
    fun `given response failure, getMovies should return a MoviesException with a message`() =
        runBlockingTest {
            val messageError = "Movies message error"
            val responseDtoBuilder = MoviesResponseDtoBuilder<List<MovieDto>>()
                .statusMessage(messageError).withStatusCode(401)

            coEvery {
                moviesService.getPopularMovies(PAGE, LANGUAGE)
            } returns responseDtoBuilder.build()

            val getMoviesFlow = moviesRepository.getMovies(PAGE)

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

            val getMoviesFlow = moviesRepository.getMovies(PAGE)

            getMoviesFlow
                .catch { assertTrue(it is ClassCastException) }
                .collect()
        }

    @Test
    fun `getReviews should return a list of reviews`() = runBlockingTest {
        val reviewsResponseDtoBuilder =
            ReviewsResponseDtoBuilder().withResults(listOf(ReviewDtoBuilder().build()))

        mockGetReviewsWith(reviewsResponseDtoBuilder).collect {
            coVerify(exactly = 1) { moviesService.getMovieReview(MOVIE_ID) }
            assertEquals(reviewsResponseDtoBuilder.build().results?.toModel(), it)
        }
    }

    @Test
    fun `given an empty list, getReviews should return a review exception`() = runBlockingTest {
        val reviewsResponseDtoBuilder = ReviewsResponseDtoBuilder().withResults(listOf())

        mockGetReviewsWith(reviewsResponseDtoBuilder)
            .catch { assertTrue(it is ReviewsException) }
            .collect()

    }

    @Test
    fun `given a null list, getReviews should return a review exception`() = runBlockingTest {
        val reviewsResponseDtoBuilder = ReviewsResponseDtoBuilder()

        mockGetReviewsWith(reviewsResponseDtoBuilder)
            .catch { assertTrue(it is ReviewsException) }
            .collect()
    }

    @Test
    fun `given response failure, getReviews should return a ReviewsException`() =
        runBlockingTest {
            val responseDtoBuilder = ReviewsResponseDtoBuilder().withStatusCode(401)

            coEvery {
                moviesService.getMovieReview(MOVIE_ID)
            } returns responseDtoBuilder.build()

            val getReviewsFlow = moviesRepository.getReviews(MOVIE_ID)

            getReviewsFlow
                .catch {
                    assertTrue(it is ReviewsException)
                }
                .collect()
        }

    @Test
    fun `given an exception, getReviews should return the ReviewsException`() =
        runBlockingTest {
            coEvery {
                moviesService.getMovieReview(MOVIE_ID)
            } throws ClassCastException()

            val getReviewsFlow = moviesRepository.getReviews(MOVIE_ID)

            getReviewsFlow
                .catch { assertTrue(it is ClassCastException) }
                .collect()
        }

    private suspend fun verifyGetMoviesWith(
        moviesResponseDtoBuilder: MoviesResponseDtoBuilder<List<MovieDto>>,
        expected: List<Movie>?
    ) {
        coEvery {
            moviesService.getPopularMovies(PAGE, LANGUAGE)
        } returns moviesResponseDtoBuilder.build()

        val getMoviesFlow = moviesRepository.getMovies(PAGE)

        getMoviesFlow.collect {
            coVerify(exactly = 1) { moviesService.getPopularMovies(PAGE, LANGUAGE) }
            assertEquals(expected, it)
        }
    }

    private suspend fun mockGetReviewsWith(reviewsResponseDtoBuilder: ReviewsResponseDtoBuilder): Flow<List<Review>> {
        coEvery {
            moviesService.getMovieReview(MOVIE_ID)
        } returns reviewsResponseDtoBuilder.build()

        return moviesRepository.getReviews(MOVIE_ID)
    }
}