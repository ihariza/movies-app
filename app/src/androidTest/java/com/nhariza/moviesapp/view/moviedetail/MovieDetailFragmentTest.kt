package com.nhariza.moviesapp.view.moviedetail

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.ResponseDtoBuilder
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.view.base.BaseAndroidTest
import com.nhariza.moviesapp.view.base.MockServerDispatcher
import com.nhariza.moviesapp.view.main.MainActivity
import com.nhariza.moviesapp.view.movies.MovieViewHolder
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class MovieDetailFragmentTest : BaseAndroidTest() {


    private lateinit var activityScenario: ActivityScenario<MainActivity>

    override fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun showMovieDetail() {
        val movies = mutableListOf(MovieDtoBuilder().build())

        mockMoviesData(movies)
        Espresso.onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieViewHolder>(0, ViewActions.click())
        )
        assertDisplayed(movies[0].title!!)
        assertDisplayed(movies[0].releaseDate!!)
        assertDisplayed(movies[0].overview!!)
    }

    private fun mockMoviesData(moviesListDto: List<MovieDto> = listOf(MovieDtoBuilder().build())) {
        val responseDto = ResponseDtoBuilder<List<MovieDto>>()
            .withResults(moviesListDto)
        mockServer.dispatcher = MockServerDispatcher.RequestDispatcher(responseDto)
    }

}