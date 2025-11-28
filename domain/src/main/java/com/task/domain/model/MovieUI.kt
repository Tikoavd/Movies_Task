package com.task.domain.model

data class MovieUI(
    override val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val genres: List<GenreUI>,
    val overview: String,
    val releaseDate: String,
): DiffUtilModel<Int>()
