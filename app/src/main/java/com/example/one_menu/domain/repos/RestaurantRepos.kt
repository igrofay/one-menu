package com.example.one_menu.domain.repos

import com.example.one_menu.domain.model.menu.MenuRestaurantModel
import com.example.one_menu.domain.model.menu.RestaurantModel

interface RestaurantRepos {
    suspend fun getInformationAboutRestaurant(restaurantId: Int) : RestaurantModel
    suspend fun getMenuRestaurant(restaurantId: Int) : MenuRestaurantModel
}