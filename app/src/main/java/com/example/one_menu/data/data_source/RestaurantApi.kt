package com.example.one_menu.data.data_source

import com.example.one_menu.di.AuthorizedClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class RestaurantApi @Inject constructor(
    @AuthorizedClient
    private val client: HttpClient
)  {
    suspend fun getInformation(restaurantId: Int) = client.get("/restaurant/information/$restaurantId")
    suspend fun getMenu(restaurantId: Int) = client.get("/restaurant/menu/$restaurantId")
}