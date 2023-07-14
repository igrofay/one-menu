package com.example.one_menu.feature.profile.model

import com.example.one_menu.feature.common.model.mvi.UISideEffect

sealed class ProfileSideEffect : UISideEffect(){
    class Message(val text: String) : ProfileSideEffect()
}
