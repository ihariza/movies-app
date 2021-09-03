package com.nhariza.moviesapp.view.movies

import app.cash.turbine.test
import com.nhariza.moviesapp.base.BaseViewModelTest
import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.model.ExceptionFlowBuilder
import com.nhariza.moviesapp.builder.model.MoviesListFlowBuilder
import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.toModel
import com.nhariza.moviesapp.view.common.Pager
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
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
        private const val PAGE = 1
        private const val QUERY = "movie"
    }

    @RelaxedMockK
    lateinit var moviesRepository: MoviesRepository

    @SpyK(recordPrivateCalls = true)
    var pager: Pager = Pager()

    private lateinit var moviesViewModel: MoviesViewModel

    override fun setup() {
        moviesViewModel = MoviesViewModel(moviesRepository, pager)
    }

    @Test
    fun `getMovies should change MoviesState to Success and return a movies list`() = runBlocking {
        val moviesDto = listOf(MovieDtoBuilder().build())
        val moviesFlow = MoviesListFlowBuilder().withMoviesListDto(moviesDto).build()

        coEvery {
            moviesRepository.getMovies(PAGE)
        } returns moviesFlow

        moviesViewModel.moviesState.test {
            moviesViewModel.getMovies()
            assertEquals(MoviesState.Loading, expectItem())
            assertEquals(MoviesState.Success(moviesDto.toModel()), expectItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getMovies exception should change MoviesState to Error`() = runBlocking {
        val exception = UnknownHostException()
        coEvery {
            moviesRepository.getMovies(PAGE)
        } returns ExceptionFlowBuilder<List<Movie>>()
            .withException(exception)
            .build()

        moviesViewModel.moviesState.test {
            moviesViewModel.getMovies()
            assertEquals(MoviesState.Loading, expectItem())
            assert(expectItem() is MoviesState.Error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `queryRequest should change MoviesState to Success and return a movies list`() =
        runBlocking {
            val moviesDto = listOf(MovieDtoBuilder().build())
            val moviesFlow = MoviesListFlowBuilder().withMoviesListDto(moviesDto).build()

            coEvery {
                moviesRepository.searchMovie(QUERY, PAGE)
            } returns moviesFlow

            moviesViewModel.moviesState.test {
                moviesViewModel.queryRequest(QUERY)
                assertEquals(MoviesState.Loading, expectItem())
                assertEquals(MoviesState.Success(moviesDto.toModel()), expectItem())
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun `empty query should call getMovies`() = runBlocking {
        val moviesDto = listOf(MovieDtoBuilder().build())
        val moviesFlow = MoviesListFlowBuilder().withMoviesListDto(moviesDto).build()

        coEvery {
            moviesRepository.getMovies(PAGE)
        } returns moviesFlow

        moviesViewModel.moviesState.test {
            moviesViewModel.queryRequest("")
            assertEquals(MoviesState.Loading, expectItem())
            assertEquals(MoviesState.Success(moviesDto.toModel()), expectItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}