package com.nhariza.moviesapp.view.movies.adapter

import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.databinding.MovieItemBinding
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.movies.MoviesFragmentDirections

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val baseImageUrl: String
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        with(binding) {
            title.text = movie.title

            if (movie.overview.isNullOrEmpty()) {
                overview.text = binding.root.context.getString(R.string.movies_no_overview)
            } else {
                overview.text = movie.overview
            }

            image.transitionName = "image_${movie.id}"
            Glide.with(root)
                .load("$baseImageUrl${movie.backdropPath}")
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(image)
        }

        setOnClickListener(movie)
    }

    private fun setOnClickListener(movie: Movie) {
        binding.root.setOnClickListener {
            val directions = MoviesFragmentDirections.viewMovieDetail(movie)
            val extras = FragmentNavigatorExtras(
                binding.image to "image_${movie.id}"
            )
            val controller = it.findNavController()
            if (controller.currentDestination?.id == R.id.moviesFragment) {
                controller.navigate(directions, extras)
            }
        }
    }
}