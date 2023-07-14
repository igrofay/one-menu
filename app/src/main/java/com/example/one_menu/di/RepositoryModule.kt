package com.example.one_menu.di

import com.example.one_menu.data.repos.AppReposImpl
import com.example.one_menu.data.repos.AuthReposImpl
import com.example.one_menu.data.repos.ClientReposImpl
import com.example.one_menu.data.repos.RestaurantReposImpl
import com.example.one_menu.domain.repos.AppRepos
import com.example.one_menu.domain.repos.AuthRepos
import com.example.one_menu.domain.repos.ClientRepos
import com.example.one_menu.domain.repos.RestaurantRepos
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAppRepos(appReposImpl: AppReposImpl) : AppRepos
    @Binds
    abstract fun bindAuthRepos(authReposImpl: AuthReposImpl) : AuthRepos
    @Binds
    abstract fun bindClientRepos(clientReposImpl: ClientReposImpl) : ClientRepos
    @Binds
    abstract fun bindRestaurantRepos(restaurantReposImpl: RestaurantReposImpl): RestaurantRepos
}