package com.example.one_menu.domain.use_case.auth

import com.example.one_menu.domain.repos.AppRepos
import com.example.one_menu.domain.repos.AuthRepos
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepos: AuthRepos,
    private val appRepos: AppRepos,
) {
    suspend fun execute(emailOrPhone: String, password: String) = runCatching {
        val tokenModel = authRepos.signIn(emailOrPhone, password)
        appRepos.setToken(tokenModel.token)
    }
}