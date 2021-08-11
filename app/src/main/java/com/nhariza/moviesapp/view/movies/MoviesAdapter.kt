package com.nhariza.moviesapp.view.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.nhariza.moviesapp.EnvironmentConfig
import com.nhariza.moviesapp.FlavorEnvironmentConfig
import com.nhariza.moviesapp.databinding.MovieItemBinding
import com.nhariza.moviesapp.repository.model.Movie
import org.koin.java.KoinJavaComponent.inject

class MoviesAdapter internal constructor(
    private val moviesListener: MovieListener?
) : ListAdapter<Movie, MovieViewHolder>(MoviesDiffCallback()) {

    private val glideRequestManager: RequestManager by inject(RequestManager::class.java)
    private val environmentConfig: EnvironmentConfig by inject(FlavorEnvironmentConfig::class.java)

    fun interface MovieListener {
        fun onMovieClicked(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieRowBinding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MovieViewHolder(
            movieRowBinding,
            glideRequestManager,
            environmentConfig.imageUrl,
            moviesListener
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = currentList[position]
        holder.bind(movie)
    }
}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}