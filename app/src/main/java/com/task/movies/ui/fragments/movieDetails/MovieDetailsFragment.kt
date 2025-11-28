package com.task.movies.ui.fragments.movieDetails

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.task.domain.model.MovieUI
import com.task.movies.R
import com.task.movies.appbase.FragmentBaseNCMVVM
import com.task.movies.appbase.utils.viewBinding
import com.task.movies.databinding.FragmentMovieDetailsBinding
import com.task.movies.ui.fragments.movieDetails.adapter.GenresAdapter
import com.task.movies.ui.fragments.movieDetails.nc.MovieDetailsNavigationCommand
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailsFragment : FragmentBaseNCMVVM<
        MovieDetailsNavigationCommand,
        MovieDetailsViewModel,
        FragmentMovieDetailsBinding>() {

    override val viewModel: MovieDetailsViewModel by viewModel {
        parametersOf(args.id)
    }
    override val binding: FragmentMovieDetailsBinding by viewBinding()
    private val args: MovieDetailsFragmentArgs by navArgs()

    private val genresAdapter: GenresAdapter by lazy { GenresAdapter() }

    override fun initView() {
        viewModel.isLoading.onEach {
            showLoading(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        val manager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        with(binding) {
            rvGenres.adapter = genresAdapter
            rvGenres.layoutManager = manager
        }
        viewModel.movie.onEach { movie ->
            movie?.let { setMovieData(it) }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun initListeners() {
        with(binding) {
            vBackButton.setOnClickListener {
                viewModel.handleNavigation(MovieDetailsNavigationCommand.Back)
            }
        }
    }

    private fun setMovieData(movie: MovieUI) {
        with(binding) {
            tvTitle.text = movie.title
            tvRating.text = getString(R.string.movie_rating_format).format(movie.voteAverage)
            tvReleaseDate.text = getString(R.string.movie_details_release_date_format).format(movie.releaseDate)
            tvOverview.text = movie.overview
            Glide.with(this@MovieDetailsFragment)
                .load(movie.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_warning)
                .into(binding.imgPoster)
            genresAdapter.submitList(movie.genres)
        }
    }
}
