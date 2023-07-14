package com.example.one_menu.data.repos

import com.example.one_menu.data.data_source.RestaurantApi
import com.example.one_menu.data.model.menu.MenuRestaurantBody
import com.example.one_menu.data.model.menu.RestaurantBody
import com.example.one_menu.domain.model.error.MenuRestaurantError
import com.example.one_menu.domain.model.menu.MenuRestaurantModel
import com.example.one_menu.domain.model.menu.RestaurantModel
import com.example.one_menu.domain.repos.RestaurantRepos
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class RestaurantReposImpl @Inject constructor(
    private val restaurantApi: RestaurantApi
): RestaurantRepos {
    override suspend fun getInformationAboutRestaurant(restaurantId: Int): RestaurantModel {
        try {
            return restaurantApi.getInformation(restaurantId).body<RestaurantBody>()
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.NotFound -> throw MenuRestaurantError.RestaurantNotFount
                else-> throw e
            }
        }
    }

    override suspend fun getMenuRestaurant(restaurantId: Int): MenuRestaurantModel {
        try {
            return restaurantApi.getMenu(restaurantId).body<MenuRestaurantBody>()
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.NotFound -> throw MenuRestaurantError.RestaurantNotFount
                else-> throw e
            }
        }
    }
}