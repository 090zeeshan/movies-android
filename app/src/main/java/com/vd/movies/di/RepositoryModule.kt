package com.vd.movies.di

import com.vd.movies.data.repository.Repository
import com.vd.movies.data.repository.RepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsRepository(repositoryImp: RepositoryImp): Repository
}