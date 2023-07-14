package com.example.one_menu.feature.auth.model.sign_up

import com.example.one_menu.feature.common.model.mvi.UISideEffect

sealed class SignUpSideEffect : UISideEffect(){
    class Message(val text: String) : SignUpSideEffect()
    object VerificationIDCreated : SignUpSideEffect()
}
