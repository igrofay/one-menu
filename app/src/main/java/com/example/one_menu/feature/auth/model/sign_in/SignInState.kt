package com.example.one_menu.feature.auth.model.sign_in

import com.example.one_menu.feature.common.model.mvi.UIState


data class SignInState(
    val emailOrPhone:String = "",
    val isErrorEmailOrPhone: Boolean = false,
    val password: String = "",
    val isErrorPassword: Boolean = false,
) : UIState()
