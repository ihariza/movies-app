package com.nhariza.moviesapp.injection

import com.nhariza.moviesapp.view.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    viewModel { MoviesViewModel(get()) }
}