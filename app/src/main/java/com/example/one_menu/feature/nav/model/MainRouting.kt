package com.example.one_menu.feature.nav.model

sealed class MainRouting(val route: String){
    object Profile : MainRouting("${route}_profile")
    object Menu : MainRouting("${route}_menu")
    object Support : MainRouting("${route}_support")
    companion object{
        const val route = "main_routing"
        val routes by lazy {
            listOf(Profile, Menu, Support)
        }
    }
}
