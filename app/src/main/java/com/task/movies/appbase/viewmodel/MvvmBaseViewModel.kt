package com.task.movies.appbase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.movies.appbase.nc.*
import com.task.movies.appbase.navigation.NavEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MvvmBaseViewModel<NC: NavigationCommand> :
    BaseViewModel() {

    abstract fun handleNavigation(navigationCommand: NC)

    fun handleNavigate(destination: NavEvent){
        viewModelScope.launch {
            navEvents.emit(destination)
        }
    }

    protected open fun onNavigate(navigationCommand: NC) {
        handleNavigation(navigationCommand)
    }

}
