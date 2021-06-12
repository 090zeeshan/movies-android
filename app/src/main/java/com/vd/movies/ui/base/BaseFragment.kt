package com.vd.movies.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.vd.movies.navigation.NavigationCommand
import com.vd.movies.ui.MainActivityDelegate
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment(val isDrawerEnabled: Boolean) : Fragment() {
    protected lateinit var mainActivityDelegate: MainActivityDelegate


    constructor():this(true)

    override fun onAttach(context: Context) {
        Log.i("BF", "onAttach")
        super.onAttach(context)
        mainActivityDelegate = context as MainActivityDelegate
    }

    override fun onStart() {
        Log.i("BF", "onStart")
        super.onStart()
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

    abstract fun getViewModel(): BaseViewModel?
}