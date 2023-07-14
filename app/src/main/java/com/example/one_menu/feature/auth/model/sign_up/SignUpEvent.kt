package com.example.one_menu.feature.auth.model.sign_up

import com.example.one_menu.feature.common.model.mvi.UIEvent

sealed class SignUpEvent: UIEvent(){
    class SelectedImage(val image: String) : SignUpEvent()
    class InputName(val name: String) : SignUpEvent()
    class InputSurname(val surname: String) : SignUpEvent()
    class InputPhone(val phone: String): SignUpEvent()
    class InputEmail(val email: String): SignUpEvent()
    class InputPassword(val password: String) : SignUpEvent()
    class InputCountry(val country: String): SignUpEvent()
    class InputCity(val city: String): SignUpEvent()
    object CreateAccount : SignUpEvent()
}
