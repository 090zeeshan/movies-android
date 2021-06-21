package com.vd.movies.di

import com.vd.movies.data.repository.Repository
import com.vd.movies.data.repository.RepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
interface RepositoryModule {
    @ActivityRetainedScoped
    @Binds
    fun bindsRepository(repositoryImp: RepositoryImp): Repository
}