package com.example.one_menu.domain.use_case.menu

import com.example.one_menu.domain.model.menu.MenuDishModel
import com.example.one_menu.domain.repos.RestaurantRepos
import javax.inject.Inject

class GetMenuRestaurantUseCase @Inject constructor(
    private val menuRepos: RestaurantRepos,
){
    suspend fun execute(restaurantId:Int) = runCatching {
        val data = menuRepos.getMenuRestaurant(restaurantId)
        val answer = mutableMapOf<Int, MutableList<MenuDishModel>>()
        data.dishes.forEach{
            answer[it.categoriesId]?.add(it) ?: answer.put(it.categoriesId, mutableListOf(it))
        }
        return@runCatching data.categories to (answer as Map<Int, List<MenuDishModel>>)
    }
}