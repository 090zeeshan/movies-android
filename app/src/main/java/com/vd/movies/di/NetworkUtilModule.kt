package com.vd.movies.di

import com.vd.movies.ui.util.NetworkUtil
import com.vd.movies.ui.util.NetworkUtilImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkUtilModule {
    @Singleton
    @Binds
    fun bindsNetworkUtil(networkUtilImp: NetworkUtilImp): NetworkUtil
}