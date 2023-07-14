package com.example.one_menu.domain.model.menu

interface MenuRestaurantModel {
    val categories: List<MenuCategoryModel>
    val dishes: List<MenuDishModel>
}