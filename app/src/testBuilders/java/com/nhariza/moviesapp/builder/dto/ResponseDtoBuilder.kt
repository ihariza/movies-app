package com.nhariza.moviesapp.builder.dto

import com.nhariza.moviesapp.repository.datasource.model.ResponseDto


class ResponseDtoBuilder<T> {

    private var page: Int = 200
    private var results: T? = null
    private var totalPages: Int? = null
    private var totalResults: Int? = null
    private var statusMessage: String? = null
    private var statusCode: Int? = null

    fun withStatusCode(statusCode: Int?): ResponseDtoBuilder<T> {
        this.statusCode = statusCode
        return this
    }

    fun statusMessage(statusMessage: String?): ResponseDtoBuilder<T> {
        this.statusMessage = statusMessage
        return this
    }

    fun withResults(results: T?): ResponseDtoBuilder<T> {
        this.results = results
        return this
    }

    fun build(): ResponseDto<T> = ResponseDto(
        page,
        results,
        totalPages,
        totalResults,
        statusMessage,
        statusCode
    )
}