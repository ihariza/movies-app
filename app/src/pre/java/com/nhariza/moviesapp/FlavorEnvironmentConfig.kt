package com.nhariza.marvelapp

class FlavorEnvironmentConfig : EnvironmentConfig() {
    override val baseUrl: String = "https://api.themoviedb.org/"
    override val imageUrl: String = "https://image.tmdb.org/t/p/w500"
    override val apikey: String = "6fb34bbf06cb15d7198ec7800c9c5d45"
}