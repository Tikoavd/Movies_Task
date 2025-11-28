package com.task.movies.appbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.task.movies.R
import com.task.movies.appbase.navigation.NavEvent
import com.task.movies.appbase.nc.NavigationCommand
import com.task.movies.appbase.viewmodel.MvvmBaseViewModel
import com.task.movies.ui.dialog.ErrorDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class FragmentBaseMVVM<
        NC : NavigationCommand,
        VM : MvvmBaseViewModel<NC>,
        ViewBind : ViewBinding> : Fragment() {

    abstract val viewModel: VM
    abstract val binding: ViewBind

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apiException.onEach {
            ErrorDialog.newInstance(it.errorInfo.message.orEmpty()) {
                viewModel.handleNavigate(NavEvent.Back)
            }.show(childFragmentManager, ErrorDialog.TAG)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        initView()
        initListeners()
    }

    fun showLoading(shouldShow: Boolean) {
        val rootView = binding.root as ViewGroup
        var loadingView: FrameLayout? = rootView.findViewById(R.id.loadingView)
        if (loadingView == null) {
            val inflater = LayoutInflater.from(binding.root.context)
            loadingView = inflater.inflate(R.layout.layout_loading, rootView, false) as FrameLayout
            rootView.addView(loadingView)
        }

        loadingView.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    protected abstract fun initView()

    protected open fun initListeners() {}
}
