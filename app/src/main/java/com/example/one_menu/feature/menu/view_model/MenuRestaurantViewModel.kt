package com.example.one_menu.feature.menu.view_model

import androidx.lifecycle.viewModelScope
import com.example.one_menu.domain.model.error.MenuRestaurantError
import com.example.one_menu.domain.model.menu.MenuDishModel
import com.example.one_menu.domain.repos.RestaurantRepos
import com.example.one_menu.domain.use_case.menu.GetMenuRestaurantUseCase
import com.example.one_menu.feature.common.view_model.AppVM
import com.example.one_menu.feature.menu.model.MenuRestaurantEvent
import com.example.one_menu.feature.menu.model.MenuRestaurantSideEffect
import com.example.one_menu.feature.menu.model.MenuRestaurantState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@HiltViewModel
class MenuRestaurantViewModel @Inject constructor(
    private val menuRepos: RestaurantRepos,
    private val getMenuRestaurantUseCase: GetMenuRestaurantUseCase,
) : AppVM<MenuRestaurantState, MenuRestaurantSideEffect, MenuRestaurantEvent>(){

    private var isLoad = false
    override val container: Container<MenuRestaurantState, MenuRestaurantSideEffect> =
        viewModelScope.container(MenuRestaurantState())

    override fun onEvent(event: MenuRestaurantEvent) {
        when(event){
            is MenuRestaurantEvent.OpenInformationAboutDish -> openInformationDish(event.dishModel)
            is MenuRestaurantEvent.QRCodeHasBeenRead -> searchRestaurant(event.text)
        }
    }
    private fun searchRestaurant(id: String) = intent{
        if (isLoad) return@intent
        isLoad = true
        val restaurantId = id.toIntOrNull()
            ?: return@intent postSideEffect(MenuRestaurantSideEffect.Message("Ресторан не найден"))
        runCatching {
            menuRepos.getInformationAboutRestaurant(restaurantId)
        }.onSuccess {
            reduce { state.copy(informationRestaurant = it) }
            postSideEffect(MenuRestaurantSideEffect.InformationAboutRestaurant)
            getMenuRestaurantUseCase.execute(restaurantId)
                .onSuccess {
                    reduce {
                        state.copy(
                            listCategory = it.first,
                            dishes = it.second
                        )
                    }
                }
        }.onFailure(::onError)
        isLoad = false
    }
    private fun openInformationDish(menuDishModel: MenuDishModel) = intent {
        reduce { state.copy(currentInformationAboutDish = menuDishModel) }
        postSideEffect(MenuRestaurantSideEffect.InformationAboutDish)
    }
    override fun onError(error: Throwable) {
        when(error){
            MenuRestaurantError.RestaurantNotFount-> intent {
                postSideEffect(MenuRestaurantSideEffect.Message("Ресторан не найден"))
            }
        }
    }
}