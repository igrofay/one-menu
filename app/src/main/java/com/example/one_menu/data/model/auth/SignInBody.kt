package com.example.one_menu.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignInBody(
    val emailOrPhone: String,
    val password: String,
)
