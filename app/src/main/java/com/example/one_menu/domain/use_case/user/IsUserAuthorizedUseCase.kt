package com.example.one_menu.domain.use_case.user

import com.example.one_menu.domain.repos.AppRepos
import javax.inject.Inject

class IsUserAuthorizedUseCase @Inject constructor(
    private val appRepos: AppRepos,
) {
    fun execute() = appRepos.getToken() != null
}