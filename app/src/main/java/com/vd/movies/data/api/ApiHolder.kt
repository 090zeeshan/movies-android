package com.vd.movies.data.api

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

class ApiHolder constructor(val api: Api) {


    fun print() {
        Log.i("ApiHolder", "" + api)
    }

    //
    @Module
    @InstallIn(ActivityComponent::class)
    class ApiHolderModule {
        @Provides
        fun apiHolder(api: Api): ApiHolder{
            return ApiHolder(api)
        }
    }

}