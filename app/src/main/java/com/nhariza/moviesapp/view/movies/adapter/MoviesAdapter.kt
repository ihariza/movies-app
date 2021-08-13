package com.nhariza.moviesapp.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nhariza.moviesapp.EnvironmentConfig
import com.nhariza.moviesapp.FlavorEnvironmentConfig
import com.nhariza.moviesapp.databinding.MovieItemBinding
import com.nhariza.moviesapp.repository.model.Movie
import org.koin.java.KoinJavaComponent.inject

class MoviesAdapter : ListAdapter<Movie, MovieViewHolder>(MoviesDiffCallback()) {

    private val environmentConfig: EnvironmentConfig by inject(FlavorEnvironmentConfig::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieRowBinding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MovieViewHolder(
            movieRowBinding,
            environmentConfig.imageUrl
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