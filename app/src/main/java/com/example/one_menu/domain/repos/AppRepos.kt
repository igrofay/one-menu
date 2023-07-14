package com.example.one_menu.domain.repos

interface AppRepos {
    fun getToken() : String?
    fun setToken(token: String)
    fun resetToken()
}