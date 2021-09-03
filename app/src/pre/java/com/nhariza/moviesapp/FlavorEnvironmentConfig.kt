package com.nhariza.moviesapp

class FlavorEnvironmentConfig : EnvironmentConfig() {
    override val baseUrl: String = "https://api.themoviedb.org/"
    override val imageUrl: String = "https://image.tmdb.org/t/p/w500"
    override val apikey: String = BuildConfig.API_KEY
}