package com.nhariza.moviesapp.builder.dto

import com.nhariza.moviesapp.repository.datasource.model.MovieDto


class MovieDtoBuilder {

    private var adult: Boolean? = false
    private var backdropPath: String? = "/image.jpeg"
    private var genreIds: List<Int>? = listOf()
    private var id: Int? = 50
    private var originalLanguage: String? = "en"
    private var originalTitle: String? = "Movie Title Original"
    private var overview: String? = "overview"
    private var popularity: Double? = 8.7
    private var posterPath: String? = "/imagePoster.jpeg"
    private var releaseDate: String? = "05/05/2005"
    private var title: String? = "Movie Title"
    private var video: Boolean? = false
    private var voteAverage: Double? = 5.0
    private var voteCount: Int? = 546783

    fun withId(id: Int?): MovieDtoBuilder {
        this.id = id
        return this
    }

    fun withTitle(title: String?): MovieDtoBuilder {
        this.title = title
        return this
    }

    fun build(): MovieDto = MovieDto(
        adult,
        backdropPath,
        genreIds,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )
}