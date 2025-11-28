package com.task.domain.repository

import com.task.domain.model.MovieUI
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<List<MovieUI>>

    fun getMovieById(id: Int): Flow<MovieUI>
}
