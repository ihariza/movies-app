package com.nhariza.moviesapp.builder.model

import com.nhariza.moviesapp.repository.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class ExceptionFlowBuilder<out T> {

    private var exception: Exception = Exception()

    fun withException(exception: Exception): ExceptionFlowBuilder<T> {
        this.exception = exception
        return this
    }

    fun withHttpException(codeError: Int = 0): ExceptionFlowBuilder<T> {
        this.exception = HttpException(Response.error<List<Movie>>(codeError, "".toResponseBody()))
        return this
    }

    fun build(): Flow<T> = flow {
        throw exception
    }
}