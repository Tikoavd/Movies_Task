package com.task.movies.ui.fragments.movieDetails.nc

import com.task.movies.appbase.nc.NavigationCommand

sealed class MovieDetailsNavigationCommand : NavigationCommand {
    object Back : MovieDetailsNavigationCommand()
}
