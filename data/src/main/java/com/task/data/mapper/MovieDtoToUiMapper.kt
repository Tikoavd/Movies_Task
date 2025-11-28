package com.task.data.mapper

import com.task.domain.model.GenreUI
import com.task.domain.model.MovieUI
import com.task.domain.utils.orDefault
import com.task.entities.responce.MoviesResponseDto
import org.koin.core.annotation.Single

@Single
class MovieDtoToUiMapper : Mapper<MoviesResponseDto.MovieDto, MovieUI> {
    override fun transform(data: MoviesResponseDto.MovieDto): MovieUI =
        MovieUI(
            id = data.id.orDefault(),
            title = data.title.orEmpty(),
            posterPath = IMAGE_PREFIX + data.posterPath.orEmpty(),
            voteAverage = data.voteAverage.orDefault(),
            genres = data.genres.orEmpty().map {
                GenreUI(
                    id = it.id.orDefault(),
                    name = it.name.orEmpty()
                )
            },
            overview = data.overview.orEmpty(),
            releaseDate = data.releaseDate.orEmpty()
        )

    companion object {
        private const val IMAGE_PREFIX = "https://media.themoviedb.org/t/p/original"
    }
}