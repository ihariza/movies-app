package com.nhariza.moviesapp.view.moviedetail

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Transition
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
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
        setupTransition()
        setupRecyclerView()
        viewModel.loadMovie(movie)
    }

    override fun bindViewActions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movieDetailState.collect { state ->
                when (state) {
                    is MovieDetailState.SuccessMovie -> showMovieDetail(state.movie)
                    is MovieDetailState.SuccessReviews -> showReviews(state.reviews)
                    MovieDetailState.Empty -> postponeEnterTransition()
                    MovieDetailState.ErrorReviews -> hideReviews()
                }
            }
        }

        binding.ivBackButton.setOnClickListener {
            onBackPressed()
        }

        (sharedElementEnterTransition as? MaterialContainerTransform)?.addListener(object :
            Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                viewModel.getReviews()
            }

            override fun onTransitionResume(transition: Transition) { /* Nothing to do */
            }

            override fun onTransitionPause(transition: Transition) { /* Nothing to do */
            }

            override fun onTransitionCancel(transition: Transition) { /* Nothing to do */
            }

            override fun onTransitionStart(transition: Transition) { /* Nothing to do */
            }
        })
    }

    private fun setupTransition() {
        postponeEnterTransition()

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.WHITE)
        }

        enterTransition = MaterialFadeThrough().apply {
            removeTarget(R.id.icon)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        with(binding.recyclerview) {
            this.layoutManager = layoutManager
            this.adapter = reviewsAdapter
        }
    }

    private fun showMovieDetail(movie: Movie) {
        with(movie) {
            binding.title.text = title
            releaseDate?.let { binding.releaseDate.text = it }
            voteAverage?.let {
                binding.voteAverage.text =
                    getString(R.string.movie_detail_vote_average, String.format("%.1f", it))
            }
            binding.overview.text = overview
            loadHeaderImage(this)
        }
    }

    private fun loadHeaderImage(movie: Movie) {
        binding.headerImage.transitionName = "image_${movie.id}"
        binding.headerImage.load(
            "${environmentConfig.imageUrl}${movie.backdropPath}",
            R.drawable.ic_image_error
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