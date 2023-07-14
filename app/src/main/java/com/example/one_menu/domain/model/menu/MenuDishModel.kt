package com.example.one_menu.domain.model.menu

interface MenuDishModel {
    val id: Int
    val image: String?
    val name: String
    val description: String
    val components: String
    val categoriesId: Int
    val spicinessLevel: Int
    val price: Float
    val currency : String
}