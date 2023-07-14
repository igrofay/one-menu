package com.example.one_menu.data.model.menu

import com.example.one_menu.domain.model.menu.RestaurantModel
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantBody(
    override val name: String,
    override val description: String,
    override val image: String?,
) : RestaurantModel