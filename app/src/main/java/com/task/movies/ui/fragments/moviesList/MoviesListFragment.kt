package com.task.movies.ui.fragments.moviesList

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.movies.appbase.FragmentBaseNCMVVM
import com.task.movies.appbase.utils.viewBinding
import com.task.movies.databinding.FragmentMoviesListBinding
import com.task.movies.ui.fragments.moviesList.adapter.MoviesListAdapter
import com.task.movies.ui.fragments.moviesList.nc.MoviesListNavigationCommand
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : FragmentBaseNCMVVM<
        MoviesListNavigationCommand,
        MoviesListViewModel,
        FragmentMoviesListBinding>() {

    override val viewModel: MoviesListViewModel by viewModel()
    override val binding: FragmentMoviesListBinding by viewBinding()

    private val moviesListAdapter: MoviesListAdapter by lazy { MoviesListAdapter(viewModel::onMovieClick) }

    override fun initView() {
        viewModel.isLoading.onEach {
            showLoading(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        val manager = LinearLayoutManager(requireContext())
        with(binding) {
            rvData.adapter = moviesListAdapter
            rvData.layoutManager = manager
        }
        viewModel.movies.onEach {
            moviesListAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}