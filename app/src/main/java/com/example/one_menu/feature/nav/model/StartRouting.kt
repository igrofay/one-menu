package com.example.one_menu.feature.nav.model

sealed class StartRouting(val route: String) {
    object Splash : StartRouting("${route}_splash")
    object SignIn : StartRouting("${route}_sign_in")
    object SignUp : StartRouting("${route}_sign_up")
    object VerificationCode : StartRouting("${route}_verification_code")
    companion object{
        const val route = "splash_routing"
    }
}