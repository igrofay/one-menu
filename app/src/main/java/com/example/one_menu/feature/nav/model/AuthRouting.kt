package com.example.one_menu.feature.nav.model

sealed class AuthRouting(val route: String) {
    object SignIn : AuthRouting("${route}_sign_in")
    object SignUp : AuthRouting("${route}_sign_up")
    companion object{
        val route = "auth_routing"
    }
}