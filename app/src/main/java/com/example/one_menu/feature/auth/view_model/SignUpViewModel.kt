package com.example.one_menu.feature.auth.view_model

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.one_menu.domain.model.error.AuthError
import com.example.one_menu.domain.use_case.auth.PhoneVerificationUseCase
import com.example.one_menu.domain.use_case.auth.SignUpUseCase
import com.example.one_menu.feature.auth.model.sign_up.SignUpEvent
import com.example.one_menu.feature.auth.model.sign_up.SignUpSideEffect
import com.example.one_menu.feature.auth.model.sign_up.SignUpState
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
class SignUpViewModel @Inject constructor(
    private val phoneVerificationUseCase: PhoneVerificationUseCase,
    private val signUpUseCase: SignUpUseCase,
) : AppVM<SignUpState, SignUpSideEffect, SignUpEvent>() {


    override val container: Container<SignUpState, SignUpSideEffect> =
        viewModelScope.container(SignUpState())

    override fun onEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.CreateAccount -> createAccount()
            is SignUpEvent.InputCity -> changeCity(event.city)
            is SignUpEvent.InputCountry -> changeCountry(event.country)
            is SignUpEvent.InputEmail -> changeEmail(event.email)
            is SignUpEvent.InputName -> changeName(event.name)
            is SignUpEvent.InputPassword -> changePassword(event.password)
            is SignUpEvent.InputPhone -> changePhone(event.phone)
            is SignUpEvent.InputSurname -> changeSurname(event.surname)
            is SignUpEvent.SelectedImage -> changeImage(event.image)
        }
    }
    private fun changeImage(image: String) = intent{
        reduce { state.copy(image = image) }
    }
    private fun changeName(name: String) = blockingIntent {
        if (name.length <= 50)
            reduce {
                state.copy(name = name)
            }
    }

    private fun changeSurname(surname: String) = blockingIntent {
        if (surname.length <= 50)
            reduce {
                state.copy(surname = surname)
            }
    }

    private fun changeEmail(email: String) = blockingIntent {
        if (email.length <= 80)
            reduce {
                state.copy(email = email)
            }
    }

    private fun changePhone(phone: String) = blockingIntent {
        if (phone.length <= 25)
            reduce {
                state.copy(phoneNumber = phone)
            }
    }

    private fun changePassword(password: String) = blockingIntent {
        if (password.length <= 50)
            reduce {
                state.copy(
                    password = password.trim()
                )
            }
    }

    private fun changeCountry(country: String) = blockingIntent {
        if (country.length <= 50)
            reduce {
                state.copy(country = country)
            }
    }
    private fun changeCity(city: String) = blockingIntent {
        if (city.length <= 80)
            reduce {
                state.copy(city = city)
            }
    }
    private fun createAccount() = intent {
        val isErrorName = state.name.isBlank()
        val isErrorSurname = state.surname.isBlank()
        val isErrorEmail = !Patterns.EMAIL_ADDRESS.toRegex().matches(state.email)
        val isErrorPhone = !Patterns.PHONE.toRegex().matches("+${state.phoneNumber}")
        val isErrorPassword = state.password.length < 5
        val isErrorCountry = state.country.isBlank()
        val isErrorCity = state.city.isBlank()
        if (
            isErrorName || isErrorSurname ||
                    isErrorEmail || isErrorPhone ||
                    isErrorPassword || isErrorCountry ||
                    isErrorCity
        ){
            reduce {
                state.copy(
                    isErrorName = isErrorName,
                    isErrorSurname = isErrorSurname,
                    isErrorEmail = isErrorEmail,
                    isErrorPassword = isErrorPassword,
                    isErrorPhone = isErrorPhone,
                    isErrorCity = isErrorCity,
                    isErrorCountry = isErrorCountry,
                )
            }
        }else{
            phoneVerificationUseCase.execute("+${state.phoneNumber}")
                .onSuccess { verifierID ->
                    signUpUseCase.execute(verifierID,"0000", state.copy(phoneNumber = "+${state.phoneNumber}"), state.image)
                        .onSuccess {
                            postSideEffect(SignUpSideEffect.VerificationIDCreated)
                        }
                        .onFailure(::onError)
                }
                .onFailure(::onError)
        }
    }
    override fun onError(error: Throwable) {
        when (error) {
            AuthError.PhoneNumberRegistered -> intent {
                postSideEffect(SignUpSideEffect.Message("Телефон занят"))
            }
            AuthError.InvalidConfirmationCode-> intent {
                postSideEffect(SignUpSideEffect.Message("Неправильный код"))
            }
            AuthError.EmailRegistered -> intent {
                postSideEffect(SignUpSideEffect.Message("Почта занята"))
            }
        }
    }
}