package com.vd.movies.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vd.movies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import timber.log.Timber

private const val TAG = "MAct"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityDelegate {
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(TAG)
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = navHostFragment.findNavController()
        setupNavDrawer()

    }


    private fun setupNavDrawer() {
        Timber.i("setupNavDrawer start")

        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
        drawerToggle =
            ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
            )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerLayout.navView.setupWithNavController(navController)

        Timber.i("setupNavDrawer end")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return false
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun enableDrawer(isEnable: Boolean) {
        Timber.i("enableDrawer")
        drawerToggle.isDrawerIndicatorEnabled = isEnable
        drawerLayout.setDrawerLockMode(
            if (isEnable) DrawerLayout.LOCK_MODE_UNLOCKED
            else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )
    }
}