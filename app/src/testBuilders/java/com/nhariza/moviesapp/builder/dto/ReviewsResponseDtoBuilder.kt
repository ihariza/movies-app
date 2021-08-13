package com.nhariza.moviesapp.builder.dto

import com.nhariza.moviesapp.repository.datasource.model.ReviewDto
import com.nhariza.moviesapp.repository.datasource.model.ReviewResponseDto


class ReviewsResponseDtoBuilder {

    private var id: Int? = 834232
    private var results: List<ReviewDto>? = null
    private var statusMessage: String? = null
    private var statusCode: Int? = null

    fun withId(id: Int?): ReviewsResponseDtoBuilder {
        this.id = id
        return this
    }

    fun withResults(reviews: List<ReviewDto>?): ReviewsResponseDtoBuilder {
        this.results = reviews
        return this
    }

    fun withStatusCode(statusCode: Int?): ReviewsResponseDtoBuilder {
        this.statusCode = statusCode
        return this
    }

    fun build(): ReviewResponseDto = ReviewResponseDto(
        id,
        results,
        statusMessage,
        statusCode
    )
}