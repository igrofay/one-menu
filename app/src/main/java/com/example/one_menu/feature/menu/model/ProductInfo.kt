package com.example.one_menu.feature.menu.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class ProductInfo(
    val name: String,
    val components: String,
    val price: String,
    @DrawableRes val previewImage: Int,
    @DrawableRes val mainImage: Int = previewImage,
    val aboutProduct: String = "",
    val category: String = "",
    val id: String = UUID.randomUUID().toString()
)
