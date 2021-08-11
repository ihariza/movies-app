package com.nhariza.moviesapp.view.movies

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        viewModel.getMovies()
    }

    override fun bindViewActions() {
        lifecycleScope.launchWhenStarted {
            viewModel.moviesState.collect {
                when (it) {
                    is MoviesState.Success -> showMovies(it.movies)
                    is MoviesState.Error -> showError()
                    MoviesState.Loading -> showLoadingScreen()
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

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.checkRequireNewPage(layoutManager.findLastVisibleItemPosition())
            }
        })
    }

    private fun showMovies(movies: List<Movie>) {
        adapter.submitList(adapter.currentList.toList() + movies.toList())
    }

    private fun showError() {
        //TODO implement show error
    }

    private fun showLoadingScreen() {
        //TODO implement show loading
    }
}