package com.dana.githubuser.data.di

import com.dana.githubuser.data.repository.DefaultUserRepository
import com.dana.githubuser.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
     @Binds
     @Singleton
     internal abstract fun bindUserRepository(repository: DefaultUserRepository): UserRepository
}