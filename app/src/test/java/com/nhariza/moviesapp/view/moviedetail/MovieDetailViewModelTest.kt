package com.nhariza.moviesapp.view.moviedetail

import app.cash.turbine.test
import com.nhariza.moviesapp.base.BaseViewModelTest
import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.ReviewDtoBuilder
import com.nhariza.moviesapp.builder.model.ExceptionFlowBuilder
import com.nhariza.moviesapp.builder.model.ReviewsListFlowBuilder
import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.repository.model.Review
import com.nhariza.moviesapp.repository.toModel
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class MovieDetailViewModelTest : BaseViewModelTest() {

    @RelaxedMockK
    lateinit var moviesRepository: MoviesRepository

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun setup() {
        movieDetailViewModel = MovieDetailViewModel(moviesRepository)
    }

    @Test
    fun `loadMovie should change MovieDetailState to SuccessMovie and call getReviews()`() =
        runBlocking {
            val movie = MovieDtoBuilder().build().toModel()

            movieDetailViewModel.movieDetailState.test {
                movieDetailViewModel.loadMovie(movie)
                assertEquals(MovieDetailState.Empty, expectItem())
                assertEquals(MovieDetailState.SuccessMovie(movie), expectItem())
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `getReviews should change MovieDetailState to SuccessReviews and return a reviews list`() =
        runBlocking {
            val movie = MovieDtoBuilder().build().toModel()
            val reviewsDto = listOf(ReviewDtoBuilder().build())
            val reviewsFlow = ReviewsListFlowBuilder().withReviewsListDto(reviewsDto).build()

            coEvery {
                moviesRepository.getReviews(any())
            } returns reviewsFlow

            movieDetailViewModel.movieDetailState.test {
                movieDetailViewModel.loadMovie(movie)
                assertEquals(MovieDetailState.Empty, expectItem())
                movieDetailViewModel.getReviews()
                assertEquals(MovieDetailState.SuccessMovie(movie), expectItem())
                assertEquals(MovieDetailState.SuccessReviews(reviewsDto.toModel()), expectItem())
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `getReviews exception should change MovieDetailState to ErrorReviews`() = runBlocking {
        val movie = MovieDtoBuilder().build().toModel()
        val exception = UnknownHostException()

        coEvery {
            moviesRepository.getReviews(any())
        } returns ExceptionFlowBuilder<List<Review>>()
            .withException(exception)
            .build()

        movieDetailViewModel.movieDetailState.test {
            movieDetailViewModel.loadMovie(movie)
            assertEquals(MovieDetailState.Empty, expectItem())
            movieDetailViewModel.getReviews()
            assertEquals(MovieDetailState.SuccessMovie(movie), expectItem())
            assert(expectItem() is MovieDetailState.ErrorReviews)
            cancelAndConsumeRemainingEvents()
        }
    }

}