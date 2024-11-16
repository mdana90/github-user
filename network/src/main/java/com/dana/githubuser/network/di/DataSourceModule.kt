package com.dana.githubuser.network.di

import com.dana.githubuser.network.UserNetworkDataSource
import com.dana.githubuser.network.datasource.DefaultUserNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    internal abstract fun provideUserNetworkDataSource(
        dataSource: DefaultUserNetworkDataSource
    ): UserNetworkDataSource
}