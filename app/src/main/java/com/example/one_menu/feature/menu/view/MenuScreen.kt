package com.example.one_menu.feature.menu.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.one_menu.feature.menu.model.MenuRestaurantEvent
import com.example.one_menu.feature.menu.model.MenuRestaurantSideEffect
import com.example.one_menu.feature.menu.model.MenuRouting
import com.example.one_menu.feature.menu.view_model.MenuRestaurantViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MenuScreen(
    menuRestaurantViewModel: MenuRestaurantViewModel = hiltViewModel(),
) {
    val state by menuRestaurantViewModel.collectAsState()
    val navigationMenu = rememberNavController()
    val context = LocalContext.current
    menuRestaurantViewModel.collectSideEffect{ sideEffect->
        when(sideEffect){
            MenuRestaurantSideEffect.InformationAboutDish ->
                navigationMenu.navigate(MenuRouting.InformationAboutProduct.route)
            MenuRestaurantSideEffect.InformationAboutRestaurant -> navigationMenu
                .navigate(MenuRouting.InformationAboutInstitution.route)
            is MenuRestaurantSideEffect.Message -> Toast
                .makeText(context, sideEffect.text, Toast.LENGTH_SHORT)
                .show()
        }
    }
    NavHost(
        navController = navigationMenu,
        startDestination = MenuRouting.ReadQRCode.route,
    ){
        composable(MenuRouting.ReadQRCode.route){
            QRReaderScreen(
                openNotification = {
                    navigationMenu.navigate(MenuRouting.Notifications.route)
                },
                openInformationRestaurant = {
                    menuRestaurantViewModel.onEvent(MenuRestaurantEvent.QRCodeHasBeenRead(it))
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
                restaurantModel = state.informationRestaurant!!,
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
                restaurantModel = state.informationRestaurant!!,
                listCategory = state.listCategory,
                dishes = state.dishes,
                back = {
                    navigationMenu.popBackStack()
                },
                openProduct = { dish ->
                    menuRestaurantViewModel.onEvent(MenuRestaurantEvent.OpenInformationAboutDish(dish))
                }
            )
        }
        composable(MenuRouting.InformationAboutProduct.route){
            InformationAboutProductScreen(
                product = state.currentInformationAboutDish!!,
                back = {
                    navigationMenu.popBackStack()
                }
            )
        }
    }
}