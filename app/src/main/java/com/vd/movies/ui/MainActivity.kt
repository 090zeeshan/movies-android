package com.vd.movies.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainActivityDelegate {
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MA", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = navHostFragment.findNavController()
        setupNavDrawer()

//        Api().searchMovies("inception"){
//            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
//        }
    }


    private fun setupNavDrawer() {
        Log.i("MA", "setupNavDrawer start")

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

        Log.i("MA", "setupNavDrawer end")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) ?: false) {
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

        Log.i("MA", "enableDrawer")
        drawerToggle.isDrawerIndicatorEnabled = isEnable
        drawerLayout.setDrawerLockMode(
            if (isEnable) DrawerLayout.LOCK_MODE_UNLOCKED
            else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )
    }
}