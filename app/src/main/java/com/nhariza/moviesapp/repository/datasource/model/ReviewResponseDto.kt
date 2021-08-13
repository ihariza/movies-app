package com.nhariza.moviesapp.repository.datasource.model

import com.google.gson.annotations.SerializedName

data class ReviewResponseDto(
    val id: Int?,
    @SerializedName("results") val results: List<ReviewDto>?,
    @SerializedName("status_message") val statusMessage: String?,
    @SerializedName("status_code") val statusCode: Int?
)