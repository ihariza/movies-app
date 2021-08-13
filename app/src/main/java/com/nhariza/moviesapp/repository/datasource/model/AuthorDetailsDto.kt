package com.nhariza.moviesapp.repository.datasource.model

import com.google.gson.annotations.SerializedName

data class AuthorDetailsDto(
    @SerializedName("avatar_path") val avatarPath: String?,
    @SerializedName("username") val username: String?
)