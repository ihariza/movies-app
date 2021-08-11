package com.nhariza.moviesapp

import android.app.Application
import com.nhariza.moviesapp.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupInjection()
    }

    private fun setupInjection() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MoviesApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    managerModule,
                    moviesModule
                )
            )
        }
    }

}