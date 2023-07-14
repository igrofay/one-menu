package com.example.one_menu.feature.menu.model

import com.example.one_menu.domain.model.menu.MenuCategoryModel
import com.example.one_menu.domain.model.menu.MenuDishModel
import com.example.one_menu.domain.model.menu.RestaurantModel
import com.example.one_menu.feature.common.model.mvi.UIState


data class MenuRestaurantState(
    val listCategory: List<MenuCategoryModel> = listOf(),
    val dishes: Map<Int, List<MenuDishModel>> = mapOf(),
    val informationRestaurant: RestaurantModel? = null,
    val currentInformationAboutDish: MenuDishModel? = null
) : UIState()
