package com.example.one_menu.feature.menu.model

import com.example.one_menu.feature.common.model.mvi.UISideEffect

sealed class MenuRestaurantSideEffect : UISideEffect() {
    object InformationAboutRestaurant : MenuRestaurantSideEffect()
    object InformationAboutDish : MenuRestaurantSideEffect()
    class Message(val text:String): MenuRestaurantSideEffect()
}