package com.nhariza.moviesapp.view.moviedetail

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhariza.moviesapp.EnvironmentConfig
import com.nhariza.moviesapp.FlavorEnvironmentConfig
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.databinding.MovieDetailFragmentBinding
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.repository.model.Review
import com.nhariza.moviesapp.view.base.BaseFragment
import com.nhariza.moviesapp.view.common.gone
import com.nhariza.moviesapp.view.common.load
import com.nhariza.moviesapp.view.common.visible
import com.nhariza.moviesapp.view.moviedetail.adapter.ReviewsAdapter
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class MovieDetailFragment : BaseFragment<MovieDetailFragmentBinding, MovieDetailViewModel>() {

    private val args: MovieDetailFragmentArgs by navArgs()
    private val environmentConfig: EnvironmentConfig by inject(FlavorEnvironmentConfig::class.java)
    private val reviewsAdapter: ReviewsAdapter by lazy { ReviewsAdapter() }

    override val viewModel: MovieDetailViewModel by viewModel()

    override fun getViewBinding(): MovieDetailFragmentBinding =
        MovieDetailFragmentBinding.inflate(layoutInflater)

    override fun initView() {
        val movie = args.movie
        setupRecyclerView()
        viewModel.loadMovie(movie)
    }

    override fun bindViewActions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movieDetailState.collect { state ->
                when (state) {
                    is MovieDetailState.SuccessMovie -> {
                        with(state.movie) {
                            binding.title.text = title
                            releaseDate?.let { binding.releaseDate.text = it }
                            voteAverage?.let {
                                binding.voteAverage.text =
                                    getString(
                                        R.string.movie_detail_vote_average,
                                        String.format("%.1f", it)
                                    )
                            }
                            binding.overview.text = overview
                            loadHeaderImage(this)
                        }
                    }
                    is MovieDetailState.SuccessReviews -> showReviews(state.reviews)
                    MovieDetailState.Empty -> postponeEnterTransition()
                    MovieDetailState.ErrorReviews -> hideReviews()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        with(binding.recyclerview) {
            this.layoutManager = layoutManager
            this.adapter = reviewsAdapter
        }
    }

    private fun loadHeaderImage(movie: Movie) {
        binding.headerImage.transitionName = "image_${movie.id}"
        binding.headerImage.load(
            "${environmentConfig.imageUrl}${movie.backdropPath}",
            R.drawable.ic_launcher_foreground
        ) {
            startPostponedEnterTransition()
        }
    }

    private fun showReviews(reviews: List<Review>) {
        binding.review.visible()
        reviewsAdapter.submitList(reviews)
    }

    private fun hideReviews() {
        binding.review.gone()
        binding.recyclerview.gone()
    }
}