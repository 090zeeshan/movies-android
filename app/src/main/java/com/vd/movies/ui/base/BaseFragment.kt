package com.vd.movies.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.vd.movies.R
import com.vd.movies.navigation.NavigationCommand
import com.vd.movies.ui.MainActivityDelegate
import timber.log.Timber

private const val TAG = "BaseF"

abstract class BaseFragment(private val isDrawerEnabled: Boolean) : Fragment() {
    private lateinit var mainActivityDelegate: MainActivityDelegate
    private val mNavOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_from_right)
        .setExitAnim(R.anim.slide_out_to_left)
        .setPopEnterAnim(R.anim.slide_in_from_right)
        .setPopExitAnim(R.anim.slide_out_to_right)
        .setLaunchSingleTop(true)
        .build()

    protected var mNavExtras: Navigator.Extras? = null;

    constructor() : this(true) {
        Timber.tag(TAG)
    }

    override fun onAttach(context: Context) {
        Timber.i("onAttach")
        super.onAttach(context)
        mainActivityDelegate = context as MainActivityDelegate
    }

    override fun onStart() {
        Timber.i("onStart")
        super.onStart()
        mainActivityDelegate.enableDrawer(isDrawerEnabled)
        getViewModel()?.title?.observe(viewLifecycleOwner, Observer {
            mainActivityDelegate.setTitle(it)
        })
        getViewModel()?.navigationCommand?.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandled()?.let { cmd ->
                when (cmd) {
                    is NavigationCommand.To -> {
                        if (mNavExtras != null) {
                            findNavController().navigate(cmd.directions, mNavExtras!!)
                            mNavExtras = null
                        } else {
                            findNavController().navigate(cmd.directions, mNavOptions)
                        }
                    }
                }
            }
        })
    }

    abstract fun getViewModel(): BaseViewModel?

}