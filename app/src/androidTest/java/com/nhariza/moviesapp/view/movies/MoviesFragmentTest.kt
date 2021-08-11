package com.nhariza.moviesapp.view.movies

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.ResponseDtoBuilder
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import com.nhariza.moviesapp.view.base.BaseAndroidTest
import com.nhariza.moviesapp.view.base.MockServerDispatcher
import com.nhariza.moviesapp.view.main.MainActivity
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import org.junit.Test

class MoviesFragmentTest : BaseAndroidTest() {


    private lateinit var activityScenario: ActivityScenario<MainActivity>

    override fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun showMoviesList() {
        val assertionTitle = "Title 14"
        val movies = listOf(
            MovieDtoBuilder().build(),
            MovieDtoBuilder().withId(2).build(),
            MovieDtoBuilder().withId(3).build(),
            MovieDtoBuilder().withId(4).build(),
            MovieDtoBuilder().withId(5).build(),
            MovieDtoBuilder().withId(6).build(),
            MovieDtoBuilder().withId(7).build(),
            MovieDtoBuilder().withId(8).build(),
            MovieDtoBuilder().withId(9).build(),
            MovieDtoBuilder().withId(10).build(),
            MovieDtoBuilder().withId(11).build(),
            MovieDtoBuilder().withId(12).build(),
            MovieDtoBuilder().withId(13).build(),
            MovieDtoBuilder().withId(14).withTitle(assertionTitle).build(),
            MovieDtoBuilder().withId(15).build()
        )
        mockMoviesData(movies)
        Espresso.onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.scrollToPosition<MovieViewHolder>(14)
        )
        BaristaVisibilityAssertions.assertDisplayed(assertionTitle)
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.recyclerview, 15)
    }

    private fun mockMoviesData(moviesListDto: List<MovieDto> = listOf(MovieDtoBuilder().build())) {
        val responseDto = ResponseDtoBuilder<List<MovieDto>>()
            .withResults(moviesListDto)
        mockServer.dispatcher = MockServerDispatcher.RequestDispatcher(responseDto)
    }

}