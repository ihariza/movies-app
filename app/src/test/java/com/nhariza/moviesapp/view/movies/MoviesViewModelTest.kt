package com.nhariza.moviesapp.view.movies

import app.cash.turbine.test
import com.nhariza.moviesapp.base.BaseViewModelTest
import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.model.ExceptionFlowBuilder
import com.nhariza.moviesapp.builder.model.MoviesListFlowBuilder
import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.toModel
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class MoviesViewModelTest : BaseViewModelTest() {

    companion object {
        private const val LANGUAGE = "en-EN"
    }

    @RelaxedMockK
    lateinit var moviesRepository: MoviesRepository

    private lateinit var moviesViewModel: MoviesViewModel

    override fun setup() {
        moviesViewModel = MoviesViewModel(moviesRepository)
    }

    @Test
    fun `getMovies should change MoviesState to Success and return a movies list`() = runBlocking {
        val moviesDto = listOf(MovieDtoBuilder().build())
        val moviesFlow = MoviesListFlowBuilder().withMoviesListDto(moviesDto).build()

        coEvery {
            moviesRepository.getMovies(LANGUAGE)
        } returns moviesFlow

        moviesViewModel.moviesState.test {
            moviesViewModel.getMovies(LANGUAGE)
            assertEquals(MoviesState.Loading, expectItem())
            assertEquals(MoviesState.Success(moviesDto.toModel()), expectItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getMovies empty list should change MoviesState to Empty`() = runBlocking {
        val moviesFlow = MoviesListFlowBuilder().withMoviesListDto(listOf()).build()

        coEvery {
            moviesRepository.getMovies(LANGUAGE)
        } returns moviesFlow

        moviesViewModel.moviesState.test {
            moviesViewModel.getMovies(LANGUAGE)
            assertEquals(MoviesState.Loading, expectItem())
            assertEquals(MoviesState.Empty, expectItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getMovies exception should change MoviesState to Error`() = runBlocking {
        val exception = UnknownHostException()
        coEvery {
            moviesRepository.getMovies(LANGUAGE)
        } returns ExceptionFlowBuilder<List<Movie>>()
            .withException(exception)
            .build()

        moviesViewModel.moviesState.test {
            moviesViewModel.getMovies(LANGUAGE)
            assertEquals(MoviesState.Loading, expectItem())
            assertEquals(MoviesState.Error(exception), expectItem())
            cancelAndConsumeRemainingEvents()
        }
    }

}