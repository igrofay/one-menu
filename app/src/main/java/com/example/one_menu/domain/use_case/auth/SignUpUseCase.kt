package com.example.one_menu.domain.use_case.auth

import com.example.one_menu.domain.model.auth.SignUpModel
import com.example.one_menu.domain.repos.AppRepos
import com.example.one_menu.domain.repos.AuthRepos
import com.example.one_menu.domain.repos.ClientRepos
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepos: AuthRepos,
    private val appRepos: AppRepos,
    private val clientRepos: ClientRepos,
) {
    suspend fun execute(verificationID: String, code: String, signUpModel: SignUpModel, imageUrl: String?) = runCatching {
        val tokenModel = authRepos.signUp(verificationID, code, signUpModel)
        appRepos.setToken(tokenModel.token)
        imageUrl?.let {
            clientRepos.uploadImage(it)
        }
    }
}