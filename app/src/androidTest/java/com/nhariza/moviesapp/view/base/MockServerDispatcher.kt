package com.nhariza.moviesapp.view.base

import com.google.gson.Gson
import com.nhariza.moviesapp.builder.dto.MovieDtoBuilder
import com.nhariza.moviesapp.builder.dto.ResponseDtoBuilder
import com.nhariza.moviesapp.repository.datasource.PathService
import com.nhariza.moviesapp.repository.datasource.model.MovieDto
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

sealed class MockServerDispatcher {

    /**
     * Return ok response from mock server
     */
    class RequestDispatcher(
        private val moviesListDto: Any? =
            ResponseDtoBuilder<List<MovieDto>>().withResults(listOf(MovieDtoBuilder().build()))
    ) : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path?.removePrefix("/")?.substringBefore("?")) {
                PathService.GET_POPULAR_MOVIES -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(Gson().toJson(moviesListDto))
                }
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    /**
     * Return error response from mock server
     */
    class ErrorDispatcher(private val responseCode: Int) : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(responseCode)
        }
    }
}