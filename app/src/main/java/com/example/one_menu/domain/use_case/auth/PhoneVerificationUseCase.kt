package com.example.one_menu.domain.use_case.auth

import com.example.one_menu.domain.repos.AuthRepos
import javax.inject.Inject

class PhoneVerificationUseCase @Inject constructor(
    private val authRepos: AuthRepos
) {
    suspend fun execute(phone: String) = runCatching {
        authRepos.phoneNumberVerification(phone)
    }
}