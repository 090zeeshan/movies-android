package com.vd.movies.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.vd.movies.navigation.NavigationCommand
import com.vd.movies.ui.MainActivityDelegate
import timber.log.Timber

private const val TAG = "BaseF"

abstract class BaseFragment(private val isDrawerEnabled: Boolean) : Fragment() {
    private lateinit var mainActivityDelegate: MainActivityDelegate


    constructor():this(true){
        Timber.tag(TAG)
    }

    override fun onAttach(context: Context) {
        Timber.i( "onAttach")
        super.onAttach(context)
        mainActivityDelegate = context as MainActivityDelegate
    }

    override fun onStart() {
        Timber.i( "onStart")
        super.onStart()
        mainActivityDelegate.enableDrawer(isDrawerEnabled)
        getViewModel()?.title?.observe(viewLifecycleOwner, Observer {
            mainActivityDelegate.setTitle(it)
        })
        getViewModel()?.navigationCommand?.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let {
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