package com.example.one_menu.domain.model.error

sealed class MenuRestaurantError: Error() {
    object RestaurantNotFount : MenuRestaurantError()
}