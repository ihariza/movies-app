package com.nhariza.moviesapp.builder.dto

import com.nhariza.moviesapp.repository.datasource.model.AuthorDetailsDto
import com.nhariza.moviesapp.repository.datasource.model.ReviewDto


class ReviewDtoBuilder {

    private var authorDetailsDto: AuthorDetailsDto? = AuthorDetailDtoBuilder().build()
    private var content: String? = "content"
    private var id: String? = "279172"


    fun withId(id: String?): ReviewDtoBuilder {
        this.id = id
        return this
    }

    fun withContent(content: String?): ReviewDtoBuilder {
        this.content = content
        return this
    }

    fun build(): ReviewDto = ReviewDto(
        authorDetailsDto,
        content,
        id
    )
}