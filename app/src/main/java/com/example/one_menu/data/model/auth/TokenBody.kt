package com.example.one_menu.data.model.auth

import com.example.one_menu.domain.model.auth.TokenModel
import kotlinx.serialization.Serializable

@Serializable
data class TokenBody(
    override val token: String
) : TokenModel
