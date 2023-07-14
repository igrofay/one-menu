package com.example.one_menu.data.model.auth

import com.example.one_menu.data.utils.toSha256
import com.example.one_menu.domain.model.auth.SignUpModel
import kotlinx.serialization.Serializable

@Serializable
data class SignUpBody(
    override val name: String,
    override val surname: String,
    override val email: String,
    override val phoneNumber: String,
    override val password: String,
    override val country: String,
    override val city: String,
) : SignUpModel{
    companion object{
        fun SignUpModel.fromModelToSignUpBody() = SignUpBody(
            name, surname, email, phoneNumber, password.toSha256(), country, city
        )
    }
}
