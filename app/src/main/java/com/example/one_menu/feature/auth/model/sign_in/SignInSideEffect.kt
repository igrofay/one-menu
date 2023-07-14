package com.example.one_menu.feature.auth.model.sign_in

import com.example.one_menu.feature.common.model.mvi.UISideEffect


sealed class SignInSideEffect : UISideEffect(){
    class Message(val text: String) : SignInSideEffect()
    object Authorization : SignInSideEffect()
}
