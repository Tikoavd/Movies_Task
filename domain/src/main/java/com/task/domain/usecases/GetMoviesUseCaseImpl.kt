package com.task.domain.usecases

import com.task.domain.dispatcher.CoroutineDispatcherProvider
import com.task.domain.model.MovieUI
import com.task.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

interface GetMoviesUseCase {
    operator fun invoke(): Flow<List<MovieUI>>
}

@Factory
internal class GetMoviesUseCaseImpl(
    private val repository: MovieRepository,
    private val dispatcher: CoroutineDispatcherProvider
): GetMoviesUseCase {
    override fun invoke(): Flow<List<MovieUI>> =
        repository.getMovies().flowOn(dispatcher.io)
}
