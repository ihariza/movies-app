package com.nhariza.moviesapp.repository.datasource.interceptor

import com.nhariza.moviesapp.FlavorEnvironmentConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val flavorEnvironmentConfig: FlavorEnvironmentConfig
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("apikey", flavorEnvironmentConfig.apikey)
            .build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}