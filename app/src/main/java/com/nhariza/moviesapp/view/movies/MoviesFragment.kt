package com.nhariza.moviesapp.view.movies

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.databinding.MoviesFragmentBinding
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.base.BaseFragment
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<MoviesFragmentBinding, MoviesViewModel>() {


    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter {
            // TODO implement show movie detail
        }
    }

    override val viewModel: MoviesViewModel by viewModel()

    override fun getViewBinding(): MoviesFragmentBinding =
        MoviesFragmentBinding.inflate(layoutInflater)

    override fun initView() {
        setupNavigation()
        setupRecyclerView()
        viewModel.getMovies("es-ES")
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

    private fun setupNavigation() {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = adapter
    }

    private fun showMovies(movies: List<Movie>) {
        adapter.submitList(movies)
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