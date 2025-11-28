package com.task.movies.ui.fragments.moviesList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.task.domain.model.MovieUI
import com.task.movies.R
import com.task.movies.appbase.adapter.BaseAdapter
import com.task.movies.appbase.adapter.BaseViewHolder
import com.task.movies.databinding.MovieItemBinding

class MoviesListAdapter(private val onMovieClick: (MovieUI) -> Unit) :
    BaseAdapter<MovieItemBinding, MovieUI, MoviesListAdapter.MoviesListViewHolder>() {

    inner class MoviesListViewHolder(private val binding: MovieItemBinding) :
        BaseViewHolder<MovieUI, MovieItemBinding>(binding) {

        override fun bind(item: MovieUI, context: Context) {
            binding.tvTitle.text = item.title
            binding.tvRating.text = context.getString(R.string.movie_rating_format).format(item.voteAverage)
            Glide.with(context)
                .load(item.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_warning)
                .into(binding.imgPoster)
        }

        override fun onItemClick(item: MovieUI) {
            onMovieClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)

        return MoviesListViewHolder(binding)
    }
}
