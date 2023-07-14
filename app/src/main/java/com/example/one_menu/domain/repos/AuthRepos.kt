package com.example.one_menu.domain.repos

import com.example.one_menu.domain.model.auth.SignUpModel
import com.example.one_menu.domain.model.auth.TokenModel

interface AuthRepos {
    suspend fun phoneNumberVerification(phone: String) : String // VerifierID
    suspend fun signIn(emailOrPhone: String, password: String) : TokenModel
    suspend fun signUp(verifierId: String, code: String, signUpModel: SignUpModel) : TokenModel
}