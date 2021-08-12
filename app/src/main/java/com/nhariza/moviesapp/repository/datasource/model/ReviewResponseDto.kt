package com.nhariza.moviesapp.repository.datasource.model

import com.google.gson.annotations.SerializedName

data class ReviewResponseDto(
    val id: Int?,
    @SerializedName("results") val reviews: List<ReviewDto>?
)