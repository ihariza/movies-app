package com.nhariza.moviesapp.view.movies

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nhariza.moviesapp.R
import com.nhariza.moviesapp.databinding.MoviesFragmentBinding
import com.nhariza.moviesapp.repository.model.Movie
import com.nhariza.moviesapp.view.base.BaseFragment
import com.nhariza.moviesapp.view.movies.adapter.MoviesAdapter
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<MoviesFragmentBinding, MoviesViewModel>() {


    private val movieAdapter: MoviesAdapter by lazy { MoviesAdapter() }

    override val viewModel: MoviesViewModel by viewModel()

    override fun getViewBinding(): MoviesFragmentBinding =
        MoviesFragmentBinding.inflate(layoutInflater)

    override fun initView() {
        setupRecyclerView()
    }

    override fun bindViewActions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.moviesState.collect {
                when (it) {
                    is MoviesState.Success -> showMovies(it.movies)
                    is MoviesState.Error -> showError(it.action)
                    MoviesState.Loading -> showLoadingScreen()
                }
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.queryRequest(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.queryRequest(newText)
                return true
            }
        })
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        with(binding.recyclerview) {
            this.layoutManager = layoutManager
            this.adapter = movieAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    viewModel.checkRequireNewPage(layoutManager.findLastVisibleItemPosition())
                }
            })

            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun showMovies(movies: List<Movie>) {
        binding.rivLoading.stopAnimation()
        movieAdapter.submitList(movies)
    }

    private fun showError(actionCancel: () -> Unit) {
        binding.rivLoading.stopAnimation()

        showAlertDialog(
            title = getString(R.string.common_error_title),
            message = getString(R.string.common_error_subtitle),
            actionName = getString(R.string.common_retry),
            action = { viewModel.getMovies() },
            actionCancel = { actionCancel.invoke() }
        )
    }

    private fun showLoadingScreen() {
        binding.rivLoading.startAnimation()
    }
}