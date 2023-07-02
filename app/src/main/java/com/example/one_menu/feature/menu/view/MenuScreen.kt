package com.example.one_menu.feature.menu.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.one_menu.feature.menu.model.MenuRouting

@Composable
fun MenuScreen() {
    val navigationMenu = rememberNavController()
    NavHost(
        navController = navigationMenu,
        startDestination = MenuRouting.ReadQRCode.route,
    ){
        composable(MenuRouting.ReadQRCode.route){
            QRReaderScreen(
                openNotification = {
                    navigationMenu.navigate(MenuRouting.Notifications.route)
                },
                openInformationInstitution = {
                    navigationMenu.navigate(MenuRouting.InformationAboutInstitution.route)
                }
            )
        }
        composable(MenuRouting.Notifications.route){
            NotificationsScreen {
                navigationMenu.popBackStack()
            }
        }
        composable(MenuRouting.InformationAboutInstitution.route){
            InformationAboutInstitutionScreen(
                next = {
                    navigationMenu.navigate(MenuRouting.MenuInstitution.route)
                },
                back = {
                    navigationMenu.popBackStack()
                }
            )
        }
        composable(MenuRouting.MenuInstitution.route){
            MenuInstitutionScreen(
                back = {
                    navigationMenu.popBackStack()
                },
                openProduct = { productId ->
                    navigationMenu.navigate(MenuRouting.InformationAboutProduct.allRoute(productId))
                }
            )
        }
        composable(
            MenuRouting.InformationAboutProduct.allRoute(),
            arguments = listOf(navArgument(MenuRouting.InformationAboutProduct.arg1) { type = NavType.StringType })
        ){ backStackEntry ->
            val productInfo = remember(backStackEntry){
                val id = backStackEntry.arguments!!.getString(MenuRouting.InformationAboutProduct.arg1)
                all.find { it.id == id }!!
            }
            InformationAboutProductScreen(
                product = productInfo,
                back = {
                    navigationMenu.popBackStack()
                }
            )
        }
    }
}