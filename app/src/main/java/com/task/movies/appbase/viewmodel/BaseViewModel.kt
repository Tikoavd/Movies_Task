package com.task.movies.appbase.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.movies.appbase.navigation.NavEvent
import kotlinx.coroutines.flow.Flow
import com.task.domain.model.errorhandler.UiError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _showNetworkError: MutableSharedFlow<Pair<UiError, () -> Unit>> =
        MutableSharedFlow()
    val showNetworkError = _showNetworkError.asSharedFlow()

    private val _apiException = MutableSharedFlow<UiError>()
    val apiException: Flow<UiError> = _apiException

    protected val navEvents = MutableSharedFlow<NavEvent>()
    val getNavEvents: Flow<NavEvent> = navEvents

    fun onException(error: UiError) {
        viewModelScope.launch {
            _apiException.emit(error)
        }
    }

    fun getString(@StringRes strId: Int, vararg fmtArgs: Any?) =
        com.task.movies.App.appContext.getString(strId, *fmtArgs)

    fun setNavigate(destination: NavEvent){
        viewModelScope.launch {
            navEvents.emit(destination)
        }
    }
}