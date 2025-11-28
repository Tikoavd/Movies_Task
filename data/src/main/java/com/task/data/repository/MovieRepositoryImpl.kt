package com.task.data.repository

import com.task.data.dataservice.apiservice.MovieService
import com.task.data.mapper.MovieDtoToUiMapper
import com.task.data.mapper.MoviesResponseToUiMapper
import com.task.data.util.emitFlow
import com.task.domain.model.MovieUI
import com.task.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val moviesListMapper: MoviesResponseToUiMapper,
    private val movieMapper: MovieDtoToUiMapper,
) : MovieRepository {

    override fun getMovies(): Flow<List<MovieUI>> = emitFlow {
        moviesListMapper.transform(movieService.getMovies())
    }

    override fun getMovieById(id: Int): Flow<MovieUI> = emitFlow {
        movieMapper.transform(movieService.getMovieById(id))
    }
}
