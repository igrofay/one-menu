package com.example.one_menu.feature.splash.model

import com.example.one_menu.feature.common.model.mvi.UISideEffect

sealed class SplashSideEffect : UISideEffect(){
    object NeedToSignIn : SplashSideEffect()
    object Authorized : SplashSideEffect()
}
