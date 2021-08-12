package com.nhariza.moviesapp.builder.dto

import com.nhariza.moviesapp.repository.datasource.model.MoviesResponseDto


class MoviesResponseDtoBuilder<T> {

    private var page: Int = 200
    private var results: T? = null
    private var totalPages: Int? = null
    private var totalResults: Int? = null
    private var statusMessage: String? = null
    private var statusCode: Int? = null

    fun withStatusCode(statusCode: Int?): MoviesResponseDtoBuilder<T> {
        this.statusCode = statusCode
        return this
    }

    fun statusMessage(statusMessage: String?): MoviesResponseDtoBuilder<T> {
        this.statusMessage = statusMessage
        return this
    }

    fun withResults(results: T?): MoviesResponseDtoBuilder<T> {
        this.results = results
        return this
    }

    fun build(): MoviesResponseDto<T> = MoviesResponseDto(
        page,
        results,
        totalPages,
        totalResults,
        statusMessage,
        statusCode
    )
}