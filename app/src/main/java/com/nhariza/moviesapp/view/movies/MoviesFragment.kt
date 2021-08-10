package com.nhariza.moviesapp.view.movies

import androidx.lifecycle.lifecycleScope
import com.nhariza.moviesapp.databinding.MoviesFragmentBinding
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.base.BaseFragment
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MoviesFragment : BaseFragment<MoviesFragmentBinding, MoviesViewModel>() {

    override val viewModel: MoviesViewModel by viewModel()

    override fun getViewBinding(): MoviesFragmentBinding =
        MoviesFragmentBinding.inflate(layoutInflater)

    override fun initView() {
        viewModel.getMovies(Locale.getDefault().toLanguageTag())
    }

    override fun bindViewActions() {
        lifecycleScope.launchWhenStarted {
            viewModel.moviesState.collect {
                when (it) {
                    is MoviesState.Success -> showMovies(it.movies)
                    is MoviesState.Error -> showError()
                    MoviesState.Loading -> showLoadingScreen()
                    MoviesState.Empty -> showEmptyScreen()
                }
            }
        }
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

    private fun showMovies(movies: List<Movie>) {
        //TODO implement show movies
    }

    private fun showError() {
        //TODO implement show error
    }

    private fun showLoadingScreen() {
        //TODO implement show loading
    }

    private fun showEmptyScreen() {
        //TODO implement show empty
    }
}