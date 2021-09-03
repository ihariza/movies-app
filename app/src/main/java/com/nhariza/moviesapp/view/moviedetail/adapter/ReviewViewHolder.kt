package com.nhariza.moviesapp.view.moviedetail.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.databinding.ReviewItemBinding
import com.nhariza.moviesapp.repository.model.Review

class ReviewViewHolder(private val binding: ReviewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(review: Review) {
        with(binding) {
            username.text = review.username

            if (review.content.isNullOrEmpty()) {
                content.text = binding.root.context.getString(R.string.movies_no_overview)
            } else {
                content.text = review.content
            }

            Glide.with(root)
                .load("${review.avatarPath?.removePrefix("/")}")
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle_24)
                .error(R.drawable.ic_account_circle_24)
                .into(avatar)
        }
    }
}