package com.example.one_menu.data.repos

import com.example.one_menu.data.data_source.AuthApi
import com.example.one_menu.data.model.auth.PhoneBody
import com.example.one_menu.data.model.auth.SignInBody
import com.example.one_menu.data.model.auth.SignUpBody.Companion.fromModelToSignUpBody
import com.example.one_menu.data.model.auth.TokenBody
import com.example.one_menu.data.model.auth.VerifierIDBody
import com.example.one_menu.data.utils.toSha256
import com.example.one_menu.domain.model.auth.SignUpModel
import com.example.one_menu.domain.model.auth.TokenModel
import com.example.one_menu.domain.model.error.AuthError
import com.example.one_menu.domain.repos.AuthRepos
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class AuthReposImpl @Inject constructor(
    private val authApi: AuthApi,
): AuthRepos{
    override suspend fun phoneNumberVerification(phone: String): String {
        try {
            val answer = authApi.phoneVerification(PhoneBody(phone))
            return answer.body<VerifierIDBody>().verifierID
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.Conflict -> throw AuthError.PhoneNumberRegistered
                else -> throw e
            }
        }
    }

    override suspend fun signIn(emailOrPhone: String, password: String): TokenModel {
        try {
            val answer = authApi.signIn(SignInBody(emailOrPhone, password.toSha256()))
            return answer.body<TokenBody>()
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.BadRequest -> throw AuthError.WrongPassword
                HttpStatusCode.NotFound -> throw AuthError.AccountNotFound
                HttpStatusCode.Unauthorized -> throw AuthError.AccountNotFound
                else -> throw e
            }
        }
    }

    override suspend fun signUp(
        verifierId: String,
        code: String,
        signUpModel: SignUpModel,
    ): TokenModel {
        try {
            val answer = authApi.signUp(verifierId, code, signUpModel.fromModelToSignUpBody())
            return answer.body<TokenBody>()
        }catch (e: ClientRequestException){
            when(e.response.status){
                HttpStatusCode.NotAcceptable -> throw AuthError.InvalidConfirmationCode
                HttpStatusCode.Conflict -> throw AuthError.EmailRegistered
                else-> throw e
            }
        }
    }
}