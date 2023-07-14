package com.example.one_menu.data.data_source

import com.example.one_menu.data.model.auth.PhoneBody
import com.example.one_menu.data.model.auth.SignInBody
import com.example.one_menu.data.model.auth.SignUpBody
import com.example.one_menu.di.BaseClient
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AuthApi @Inject constructor(
    @BaseClient
    private val client: HttpClient,
) {
    suspend fun signIn(signInBody: SignInBody) = client.post("/auth/client/sign_in") {
        setBody(signInBody)
        contentType(ContentType.Application.Json)
    }

    suspend fun phoneVerification(phoneBody: PhoneBody) =
        client.post("/auth/client/phone_verification") {
            setBody(phoneBody)
            contentType(ContentType.Application.Json)
        }

    suspend fun signUp(verifierId: String, code: String, signUpBody: SignUpBody) =
        client.post("/auth/client/sign_up/$verifierId") {
            header("sms_code", code)
            setBody(signUpBody)
            contentType(ContentType.Application.Json)
        }
}