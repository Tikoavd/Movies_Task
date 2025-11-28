package com.task.data.mapper

import com.task.domain.model.MovieUI
import com.task.domain.utils.orDefault
import com.task.entities.responce.MoviesResponseDto
import org.koin.core.annotation.Single

@Single
class MoviesResponseToUiMapper(
    private val movieMapper: MovieDtoToUiMapper
) : Mapper<MoviesResponseDto, List<MovieUI>> {
    override fun transform(data: MoviesResponseDto): List<MovieUI> =
        data.results.orEmpty().map { movieMapper.transform(it) }
}
