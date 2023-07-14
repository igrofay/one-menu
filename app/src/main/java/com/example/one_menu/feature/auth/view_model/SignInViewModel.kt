package com.example.one_menu.feature.auth.view_model

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.one_menu.domain.model.error.AuthError
import com.example.one_menu.domain.use_case.auth.SignInUseCase
import com.example.one_menu.feature.auth.model.sign_in.SignInEvent
import com.example.one_menu.feature.auth.model.sign_in.SignInSideEffect
import com.example.one_menu.feature.auth.model.sign_in.SignInState
import com.example.one_menu.feature.common.view_model.AppVM
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : AppVM<SignInState, SignInSideEffect, SignInEvent>(){

    override val container: Container<SignInState, SignInSideEffect> =
        viewModelScope.container(SignInState())

    override fun onEvent(event: SignInEvent) {
        when(event){
            is SignInEvent.InputEmailOrPhone -> changeEmailOrPhone(event.emailOrPhone)
            is SignInEvent.InputPassword -> changePassword(event.password)
            SignInEvent.Login -> authentication()
        }
    }
    private fun changeEmailOrPhone(emailOrPhone: String) = blockingIntent {
        reduce {
            state.copy(
                emailOrPhone = emailOrPhone
            )
        }
    }

    private fun changePassword(password: String) = blockingIntent {
        reduce {
            state.copy(
                password = password.trim()
            )
        }
    }
    private fun authentication() = intent {
        val isErrorEmailOrPhone = (!Patterns.PHONE.toRegex()
            .matches("+${state.emailOrPhone}")) && (!Patterns.EMAIL_ADDRESS.toRegex()
            .matches(state.emailOrPhone))
        val isErrorPassword = state.password.length < 5
        if (isErrorEmailOrPhone || isErrorPassword)
            reduce {
                state.copy(
                    isErrorEmailOrPhone = isErrorEmailOrPhone,
                    isErrorPassword = isErrorPassword,
                )
            }
        else{
            val emailOrPhone = if (
                Patterns.EMAIL_ADDRESS.toRegex().matches(state.emailOrPhone)
            ) state.emailOrPhone else "+${state.emailOrPhone}"
            signInUseCase.execute(emailOrPhone, state.password)
                .onSuccess {
                    postSideEffect(SignInSideEffect.Authorization)
                }
                .onFailure(::onError)
        }
    }
    override fun onError(error: Throwable) {
        when(error){
            AuthError.WrongPassword-> intent {
                postSideEffect(SignInSideEffect.Message("Неправильный пароль"))
            }
            AuthError.AccountNotFound-> intent {
                postSideEffect(SignInSideEffect.Message("Аккаунт не найден"))
            }
        }
    }
}