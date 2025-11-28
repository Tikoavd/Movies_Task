package com.task.movies.ui.fragments.movieDetails

import androidx.lifecycle.viewModelScope
import com.task.domain.model.MovieUI
import com.task.domain.usecases.ErrorHandlerUseCase
import com.task.domain.usecases.GetMovieByIdUseCase
import com.task.movies.appbase.navigation.NavEvent
import com.task.movies.appbase.viewmodel.MvvmBaseViewModel
import com.task.movies.ui.fragments.movieDetails.nc.MovieDetailsNavigationCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MovieDetailsViewModel(
    movieId: Int,
    getMovieByIdUseCase: GetMovieByIdUseCase,
    private val errorHandler: ErrorHandlerUseCase
) : MvvmBaseViewModel<MovieDetailsNavigationCommand>() {

    private val _isLoading: MutableStateFlow<Boolean> by lazy { MutableStateFlow(true) }
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _movie: MutableStateFlow<MovieUI?> by lazy { MutableStateFlow(null) }
    val movie: StateFlow<MovieUI?> = _movie.asStateFlow()

    init {
        getMovieByIdUseCase(movieId).onEach { movie ->
            _movie.update { movie }
            _isLoading.update { false }
        }.catch {
            onException(errorHandler(it))
        }.launchIn(viewModelScope)
    }

    override fun handleNavigation(navigationCommand: MovieDetailsNavigationCommand) =
        when (navigationCommand) {
            is MovieDetailsNavigationCommand.Back -> handleNavigate(NavEvent.Back)
        }
}
