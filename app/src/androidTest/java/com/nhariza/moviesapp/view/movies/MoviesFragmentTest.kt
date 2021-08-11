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
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class MoviesFragmentTest : BaseAndroidTest() {


    private lateinit var activityScenario: ActivityScenario<MainActivity>

    override fun setup() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun showMoviesList() {
        val movies = mutableListOf<MovieDto>()
        for (x in 1..20) {
            movies.add(MovieDtoBuilder().withId(x).withTitle("Title $x").build())
        }

        mockMoviesData(movies)
        Espresso.onView(withId(R.id.recyclerview)).perform(
            RecyclerViewActions.scrollToPosition<MovieViewHolder>(19)
        )
        assertDisplayed("Title 14")
        assertRecyclerViewItemCount(R.id.recyclerview, 20)
    }

    @Test
    fun showErrorDialog() {
        mockServer.dispatcher = MockServerDispatcher.ErrorDispatcher(401)
        assertDisplayed(R.string.common_error_title)
        assertDisplayed(R.string.common_error_subtitle)
    }

    private fun mockMoviesData(moviesListDto: List<MovieDto> = listOf(MovieDtoBuilder().build())) {
        val responseDto = ResponseDtoBuilder<List<MovieDto>>()
            .withResults(moviesListDto)
        mockServer.dispatcher = MockServerDispatcher.RequestDispatcher(responseDto)
    }

}