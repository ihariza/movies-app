package com.nhariza.moviesapp.injection

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.nhariza.moviesapp.FlavorEnvironmentConfig
import com.nhariza.moviesapp.repository.datasource.MoviesService
import com.nhariza.moviesapp.repository.datasource.interceptor.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { FlavorEnvironmentConfig() }

    factory {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    factory { AuthorizationInterceptor(get()) }

    factory {
        OkHttpClient.Builder().apply {
            addInterceptor(get<HttpLoggingInterceptor>())
            addInterceptor(get<AuthorizationInterceptor>())
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(110, TimeUnit.SECONDS)
        }.build()
    }

    factory { GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create() }

    factory {
        Retrofit.Builder().client(get()).baseUrl(get<FlavorEnvironmentConfig>().baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(MoviesService::class.java) }
}