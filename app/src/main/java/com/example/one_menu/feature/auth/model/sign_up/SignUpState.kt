package com.example.one_menu.feature.auth.model.sign_up

import com.example.one_menu.domain.model.auth.SignUpModel
import com.example.one_menu.feature.common.model.mvi.UIState

data class SignUpState(
    val image: String? = null,
    override val name: String = "",
    val isErrorName: Boolean = false,
    override val surname: String = "",
    val isErrorSurname: Boolean = false,
    override val email: String = "",
    val isErrorEmail: Boolean = false,
    override val phoneNumber: String = "",
    val isErrorPhone: Boolean = false,
    override val password: String = "",
    val isErrorPassword: Boolean = false,
    override val country: String = "",
    val isErrorCountry: Boolean = false,
    override val city: String = "",
    val isErrorCity: Boolean = false,
) : SignUpModel, UIState()
