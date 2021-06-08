package com.vd.movies.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.vd.movies.R
import com.vd.movies.data.api.Api
import com.vd.movies.data.api.ApiHolder
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity() : AppCompatActivity() {

    @Inject
    lateinit var apiHolder: ApiHolder;

    @Inject
    lateinit var api1: Api


    @Inject
    lateinit var api2: Api


    @Inject
    lateinit var api3: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.i("hilt", "api1: "+api1)
        Log.i("hilt", "api2: "+api2)
        Log.i("hilt", "api3: "+api3)
        apiHolder.print()
//        Handler(Looper.getMainLooper()).postDelayed({
//            finish()
//            startActivity(Intent(this, MainActivity::class.java))
//        }, 2000)
    }
}