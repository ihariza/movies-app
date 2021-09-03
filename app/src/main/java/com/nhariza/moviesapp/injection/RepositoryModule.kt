package com.nhariza.moviesapp.injection

import com.nhariza.moviesapp.repository.MoviesRepository
import com.nhariza.moviesapp.repository.MoviesRepositoryImp
import org.koin.dsl.module


val repositoryModule = module {

    factory<MoviesRepository> {
        MoviesRepositoryImp(
            moviesService = get()
        )
    }
}