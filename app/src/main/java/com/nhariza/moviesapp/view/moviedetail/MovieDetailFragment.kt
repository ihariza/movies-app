package com.nhariza.moviesapp.view.moviedetail

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.nhariza.moviesapp.EnvironmentConfig
import com.nhariza.moviesapp.FlavorEnvironmentConfig
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.databinding.MovieDetailFragmentBinding
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.base.BaseFragment
import com.nhariza.moviesapp.view.common.load
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class MovieDetailFragment : BaseFragment<MovieDetailFragmentBinding, MovieDetailViewModel>() {

    private val args: MovieDetailFragmentArgs by navArgs()
    private val environmentConfig: EnvironmentConfig by inject(FlavorEnvironmentConfig::class.java)

    override val viewModel: MovieDetailViewModel by viewModel()

    override fun getViewBinding(): MovieDetailFragmentBinding =
        MovieDetailFragmentBinding.inflate(layoutInflater)

    override fun initView() {
        val movie = args.movie
        viewModel.loadMovie(movie)
    }

    override fun bindViewActions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movieDetailState.collect { state ->
                when (state) {
                    is MovieDetailState.Success -> {
                        with(state.movie) {
                            binding.title.text = title
                            releaseDate?.let { binding.releaseDate.text = it }
                            voteAverage?.let {
                                binding.voteAverage.text =
                                    getString(
                                        R.string.movie_detail_vote_verage,
                                        String.format("%.1f", it)
                                    )
                            }
                            binding.overview.text = overview
                            loadHeaderImage(this)
                        }
                    }
                    MovieDetailState.Empty -> postponeEnterTransition()
                }
            }
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
}