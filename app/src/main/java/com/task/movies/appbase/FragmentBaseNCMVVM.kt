package com.task.movies.appbase

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.viewbinding.ViewBinding
import com.task.movies.appbase.navigation.NavEvent
import com.task.movies.appbase.nc.NavigationCommand
import com.task.movies.appbase.viewmodel.MvvmBaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.navigation.findNavController

abstract class FragmentBaseNCMVVM<
        NC : NavigationCommand,
        VM : MvvmBaseViewModel<NC>,
        VB : ViewBinding> : FragmentBaseMVVM<NC, VM, VB>() {

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        collectNavigationEvents()
    }

    private fun collectNavigationEvents() {
        viewModel.getNavEvents.onEach {
            when (it) {
                is NavEvent.Back -> {
                    popBackStack()
                }
                is NavEvent.Destination -> {
                    navigate(it.destination)
                }
//                is NavEvent.DeepLink -> {
//                    val request = NavDeepLinkRequest.Builder.fromUri(it.uri).build()
//                    navigate(request)
//                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    private fun navigate(destinationId: Int, arg: Bundle? = null) {
        navController.navigate(destinationId, arg)
    }

    private fun navigate(destinations: NavDirections) {
        navController.navigate(destinations)
    }

    private fun navigate(deepLinkRequest: NavDeepLinkRequest) {
        navController.navigate(deepLinkRequest)
    }
}
