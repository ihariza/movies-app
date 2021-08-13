package com.nhariza.moviesapp.builder.model

import com.nhariza.moviesapp.builder.dto.ReviewDtoBuilder
import com.nhariza.moviesapp.repository.datasource.model.ReviewDto
import com.nhariza.moviesapp.repository.model.Review
import com.nhariza.moviesapp.repository.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReviewsListFlowBuilder {

    private var reviewsListDto: List<ReviewDto> = listOf(ReviewDtoBuilder().build())

    fun withReviewsListDto(reviewsListDto: List<ReviewDto>): ReviewsListFlowBuilder {
        this.reviewsListDto = reviewsListDto
        return this
    }

    fun build(): Flow<List<Review>> = flowOf(reviewsListDto.toModel())
}