package com.example.one_menu.data.model.menu

import com.example.one_menu.domain.model.menu.MenuDishModel
import kotlinx.serialization.Serializable

@Serializable
data class MenuDishBody(
    override val id: Int,
    override val image: String?,
    override val name: String,
    override val description: String,
    override val components: String,
    override val categoriesId: Int,
    override val spicinessLevel: Int,
    override val price: Float,
    override val currency: String
) : MenuDishModel