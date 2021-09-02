package com.nhariza.moviesapp.view.moviedetail

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.MoviesResponseDtoBuilder
import com.nhariza.moviesapp.builder.dto.ReviewDtoBuilder
import com.nhariza.moviesapp.builder.dto.ReviewsResponseDtoBuilder
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.repository.datasource.model.ReviewDto
import com.nhariza.moviesapp.view.base.BaseAndroidTest
import com.nhariza.moviesapp.view.base.MockServerDispatcher
import com.nhariza.moviesapp.view.main.MainActivity
import com.nhariza.moviesapp.view.movies.adapter.MovieViewHolder
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class MovieDetailFragmentTest : BaseAndroidTest() {


    private lateinit var activityScenario: ActivityScenario<MainActivity>

    override fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun showMovieDetail() {
        val movies = listOf(MovieDtoBuilder().build())

        mockMoviesData(movies)
        Espresso.onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(0, ViewActions.click())
        )
        assertDisplayed(movies[0].title!!)
        assertDisplayed(movies[0].releaseDate!!)
        assertDisplayed(movies[0].overview!!)
    }

    @Test
    fun showMovieDetailWithReviews() {
        val movies = listOf(MovieDtoBuilder().build())
        val reviews = listOf(ReviewDtoBuilder().build())

        mockMoviesData(movies, reviews)
        Espresso.onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(0, ViewActions.click())
        )

        assertDisplayed(movies[0].title!!)
        assertDisplayed(movies[0].releaseDate!!)
        assertDisplayed(movies[0].overview!!)
        assertDisplayed(R.string.movie_detail_review_title)
    }

    private fun mockMoviesData(
        moviesListDto: List<MovieDto> = listOf(MovieDtoBuilder().build()),
        reviewsListDto: List<ReviewDto>? = null
    ) {
        val moviesResponseDto = MoviesResponseDtoBuilder<List<MovieDto>>()
            .withResults(moviesListDto)
        val responseReviewDto = ReviewsResponseDtoBuilder()
            .withResults(reviewsListDto)
        mockServer.dispatcher = MockServerDispatcher.RequestDispatcher(
            moviesResponseDto,
            "${moviesListDto[0].id}",
            responseReviewDto
        )
    }

}