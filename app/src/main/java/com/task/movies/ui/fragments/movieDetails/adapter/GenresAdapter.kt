package com.task.movies.ui.fragments.movieDetails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.task.domain.model.GenreUI
import com.task.movies.appbase.adapter.BaseAdapter
import com.task.movies.appbase.adapter.BaseViewHolder
import com.task.movies.databinding.GenreItemBinding

class GenresAdapter :
    BaseAdapter<GenreItemBinding, GenreUI, GenresAdapter.MoviesListViewHolder>() {

    inner class MoviesListViewHolder(private val binding: GenreItemBinding) :
        BaseViewHolder<GenreUI, GenreItemBinding>(binding) {

        override fun bind(item: GenreUI, context: Context) {
            binding.tvGenre.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GenreItemBinding.inflate(inflater, parent, false)

        return MoviesListViewHolder(binding)
    }
}
