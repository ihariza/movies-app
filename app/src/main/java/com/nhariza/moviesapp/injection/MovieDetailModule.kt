package com.nhariza.moviesapp.injection

import com.nhariza.moviesapp.view.moviedetail.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailModule = module {
    viewModel { MovieDetailViewModel(get()) }
}