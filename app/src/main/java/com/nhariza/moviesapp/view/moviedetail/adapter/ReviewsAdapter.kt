package com.nhariza.moviesapp.view.moviedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nhariza.moviesapp.databinding.ReviewItemBinding
import com.nhariza.moviesapp.repository.model.Review

class ReviewsAdapter : ListAdapter<Review, ReviewViewHolder>(ReviewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val reviewRowBinding = ReviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ReviewViewHolder(reviewRowBinding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val movie = currentList[position]
        holder.bind(movie)
    }

}

class ReviewsDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}