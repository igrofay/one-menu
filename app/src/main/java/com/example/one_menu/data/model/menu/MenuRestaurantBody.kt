package com.example.one_menu.data.model.menu

import com.example.one_menu.domain.model.menu.MenuRestaurantModel
import kotlinx.serialization.Serializable

@Serializable
data class MenuRestaurantBody(
    override val categories: List<MenuCategoryBody>,
    override val dishes: List<MenuDishBody>,
) : MenuRestaurantModel