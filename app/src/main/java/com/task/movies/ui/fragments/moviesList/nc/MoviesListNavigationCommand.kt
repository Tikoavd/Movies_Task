package com.task.movies.ui.fragments.moviesList.nc

import com.task.movies.appbase.nc.NavigationCommand

sealed class MoviesListNavigationCommand : NavigationCommand {
    object Close : MoviesListNavigationCommand()
    data class GoToMovieDetails(val id: Int) : MoviesListNavigationCommand()
}
