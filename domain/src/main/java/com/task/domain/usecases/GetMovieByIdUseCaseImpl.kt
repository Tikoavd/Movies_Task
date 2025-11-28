package com.task.domain.usecases

import com.task.domain.dispatcher.CoroutineDispatcherProvider
import com.task.domain.model.MovieUI
import com.task.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory

interface GetMovieByIdUseCase {
    operator fun invoke(id: Int): Flow<MovieUI>
}

@Factory
internal class GetMovieByIdUseCaseImpl(
    private val repository: MovieRepository,
    private val dispatcher: CoroutineDispatcherProvider
): GetMovieByIdUseCase {
    override fun invoke(id: Int): Flow<MovieUI> =
        repository.getMovieById(id).flowOn(dispatcher.io)
}
