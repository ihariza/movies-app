package com.nhariza.moviesapp

class FlavorEnvironmentConfig : EnvironmentConfig() {
    override val baseUrl: String = "http://localhost:7878/"
    override val imageUrl: String = "https://image.tmdb.org/t/p/w500"
    override val apikey: String = ""
}