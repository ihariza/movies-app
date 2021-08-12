package com.nhariza.moviesapp.repository.datasource.model

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto<out T>(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: T?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?,
    @SerializedName("status_message") val statusMessage: String?,
    @SerializedName("status_code") val statusCode: Int?
)