package com.example.one_menu.feature.menu.model

sealed class MenuRouting(val route : String){
    object ReadQRCode : MenuRouting("${route}_read_qr_code")
    object Notifications : MenuRouting("${route}_notifications")
    object InformationAboutInstitution : MenuRouting("${route}_information_about_institution")
    object MenuInstitution : MenuRouting("${route}_menu_institution")
    object InformationAboutProduct : MenuRouting("${route}_information_about_product"){
        val arg1 = "productId"
        fun allRoute() = "$route/{$arg1}"
        fun allRoute(productId: String) = "$route/$productId"
    }
    companion object{
        val route = "menu_routing"
    }
}