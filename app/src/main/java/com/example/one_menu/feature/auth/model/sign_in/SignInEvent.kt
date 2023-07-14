package com.example.one_menu.feature.auth.model.sign_in

import com.example.one_menu.feature.common.model.mvi.UIEvent

sealed class SignInEvent : UIEvent(){
    class InputEmailOrPhone(val emailOrPhone: String) : SignInEvent()
    class InputPassword(val password: String) : SignInEvent()
    object Login : SignInEvent()
}
