package com.nhariza.moviesapp.builder.dto

import com.nhariza.moviesapp.repository.datasource.model.AuthorDetailsDto


class AuthorDetailDtoBuilder {

    private var avatarPath: String? = "/avatarpath"
    private var username: String? = "userName"


    fun withUsername(username: String?): AuthorDetailDtoBuilder {
        this.username = username
        return this
    }

    fun build(): AuthorDetailsDto = AuthorDetailsDto(
        avatarPath,
        username
    )
}