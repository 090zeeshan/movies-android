package com.vd.movies.di

import com.vd.movies.data.api.Api
import com.vd.movies.data.api.ApiImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface ApiModule {
    @Binds
    fun bindApi(apiImp: ApiImp): Api
}