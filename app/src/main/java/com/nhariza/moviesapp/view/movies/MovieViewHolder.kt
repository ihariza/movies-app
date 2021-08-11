package com.nhariza.moviesapp.view.movies

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.databinding.MovieItemBinding
import com.nhariza.moviesapp.repository.model.Movie

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val requestManager: RequestManager,
    private val baseImageUrl: String,
    private val moviesListener: MoviesAdapter.MovieListener?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.title.text = movie.title
        binding.overview.text = movie.overview
        requestManager
            .load("$baseImageUrl${movie.posterPath}")
            .centerCrop()
            .error(R.mipmap.ic_launcher_round)
            .into(binding.image)
        binding.root.setOnClickListener { moviesListener?.onMovieClicked(movie) }
    }
}