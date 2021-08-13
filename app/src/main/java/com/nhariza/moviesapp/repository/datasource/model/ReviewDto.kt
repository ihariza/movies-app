package com.nhariza.moviesapp.repository.datasource.model

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("author_details") val authorDetailsDto: AuthorDetailsDto?,
    @SerializedName("content") val content: String?,
    @SerializedName("id") val id: String?
)