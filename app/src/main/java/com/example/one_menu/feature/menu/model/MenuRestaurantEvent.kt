package com.example.one_menu.feature.menu.model

import com.example.one_menu.domain.model.menu.MenuDishModel
import com.example.one_menu.feature.common.model.mvi.UIEvent

sealed class MenuRestaurantEvent : UIEvent(){
    class QRCodeHasBeenRead(val text: String) : MenuRestaurantEvent()
    class OpenInformationAboutDish(val dishModel: MenuDishModel) : MenuRestaurantEvent()
}
