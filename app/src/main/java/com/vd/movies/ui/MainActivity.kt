package com.vd.movies.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vd.movies.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = navHostFragment.findNavController()
        setupNavDrawer()

//        Api().searchMovies("inception"){
//            Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
//        }
    }

    private fun setupNavDrawer() {
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
        drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
            )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerLayout.navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return false
    }
}