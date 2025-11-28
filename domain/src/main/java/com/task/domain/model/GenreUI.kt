package com.task.domain.model

data class GenreUI(
    override val id: Int,
    val name: String
): DiffUtilModel<Int>()
