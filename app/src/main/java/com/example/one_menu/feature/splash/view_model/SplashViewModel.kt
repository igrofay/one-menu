package com.example.one_menu.feature.splash.view_model

import androidx.lifecycle.viewModelScope
import com.example.one_menu.domain.use_case.user.IsUserAuthorizedUseCase
import com.example.one_menu.feature.common.model.mvi.UIEvent
import com.example.one_menu.feature.common.model.mvi.UIState
import com.example.one_menu.feature.common.view_model.AppVM
import com.example.one_menu.feature.splash.model.SplashSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase
) : AppVM<UIState.Default, SplashSideEffect, UIEvent.Default>() {

    override val container: Container<UIState.Default, SplashSideEffect> =
        viewModelScope.container(UIState.Default){
            checkIfUserIsLoggedIn()
        }

    override fun onEvent(event: UIEvent.Default) {}
    override fun onError(error: Throwable) {

    }
    private fun checkIfUserIsLoggedIn() = intent {
        if (isUserAuthorizedUseCase.execute()){
            postSideEffect(SplashSideEffect.Authorized)
        }else{
            postSideEffect(SplashSideEffect.NeedToSignIn)
        }
    }
}