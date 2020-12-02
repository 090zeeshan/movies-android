package com.vd.movies.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vd.movies.navigation.NavigationCommand
import com.vd.movies.ui.MainActivityDelegate

abstract class BaseFragment(val isDrawerEnabled: Boolean = true) : Fragment() {
    protected lateinit var mainActivityDelegate: MainActivityDelegate

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivityDelegate = context as MainActivityDelegate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityDelegate.enableDrawer(isDrawerEnabled)
        getViewModel()?.title?.observe(viewLifecycleOwner, Observer {
            mainActivityDelegate.setTitle(it)
        })
        getViewModel()?.navigationCommand?.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let {
                when (it) {
                    is NavigationCommand.To -> {
                        findNavController().navigate(it.directions)
                    }
                }

            }
        })
    }

    protected abstract fun getViewModel(): BaseViewModel?
}