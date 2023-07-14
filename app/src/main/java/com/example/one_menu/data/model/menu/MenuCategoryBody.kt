package com.example.one_menu.data.model.menu

import com.example.one_menu.domain.model.menu.MenuCategoryModel
import kotlinx.serialization.Serializable

@Serializable
data class MenuCategoryBody(
    override val id: Int,
    override val name: String
) : MenuCategoryModel