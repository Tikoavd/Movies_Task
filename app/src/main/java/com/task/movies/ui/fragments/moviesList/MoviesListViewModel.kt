package com.task.movies.ui.fragments.moviesList

import androidx.lifecycle.viewModelScope
import com.task.domain.model.MovieUI
import com.task.domain.usecases.ErrorHandlerUseCase
import com.task.domain.usecases.GetMoviesUseCase
import com.task.movies.appbase.navigation.NavEvent
import com.task.movies.appbase.navigation.toDestination
import com.task.movies.appbase.viewmodel.MvvmBaseViewModel
import com.task.movies.ui.fragments.moviesList.nc.MoviesListNavigationCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesListViewModel(
    getMoviesUseCase: GetMoviesUseCase,
    private val errorHandler: ErrorHandlerUseCase
) : MvvmBaseViewModel<MoviesListNavigationCommand>() {

    private val _isLoading: MutableStateFlow<Boolean> by lazy { MutableStateFlow(true) }
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _movies: MutableStateFlow<List<MovieUI>> by lazy { MutableStateFlow(emptyList()) }
    val movies: StateFlow<List<MovieUI>> = _movies.asStateFlow()

    init {
        getMoviesUseCase().onEach { movies ->
            _movies.update { movies }
            _isLoading.update { false }
        }.catch {
            onException(errorHandler(it))
        }.launchIn(viewModelScope)
    }

    fun onMovieClick(movie: MovieUI) {
        handleNavigation(MoviesListNavigationCommand.GoToMovieDetails(movie.id))
    }

    override fun handleNavigation(navigationCommand: MoviesListNavigationCommand) =
        when (navigationCommand) {
            is MoviesListNavigationCommand.Close -> handleNavigate(NavEvent.Back)
            is MoviesListNavigationCommand.GoToMovieDetails -> handleNavigate(
                MoviesListFragmentDirections.actionMoviesListToMovieDetails(navigationCommand.id)
                    .toDestination()
            )
        }
}
