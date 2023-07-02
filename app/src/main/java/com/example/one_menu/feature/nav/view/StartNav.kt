package com.example.one_menu.feature.nav.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.one_menu.feature.auth.view.SignInScreen
import com.example.one_menu.feature.auth.view.SignUpScreen
import com.example.one_menu.feature.auth.view.VerificationCodeScreen
import com.example.one_menu.feature.nav.model.MainRouting
import com.example.one_menu.feature.nav.model.StartRouting
import com.example.one_menu.feature.splash.view.SplashScreen

fun NavGraphBuilder.startNav(
    appNavController: NavController,
) {
    navigation(
        startDestination = StartRouting.Splash.route,
        route = StartRouting.route
    ) {
        composable(StartRouting.Splash.route) {
            SplashScreen {
                appNavController.navigate(StartRouting.SignIn.route) {
                    popUpTo(StartRouting.Splash.route) { inclusive = true }
                }
            }
        }
        composable(StartRouting.SignIn.route) {
            SignInScreen(
                openContent = {
                    appNavController.navigate(MainRouting.route) {
                        popUpTo(StartRouting.SignIn.route) { inclusive = true }
                    }
                },
                openRegistration = {
                    appNavController.navigate(StartRouting.SignUp.route)
                }
            )
        }
        composable(StartRouting.SignUp.route){
            SignUpScreen {
                appNavController.navigate(StartRouting.VerificationCode.route)
            }
        }
        composable(StartRouting.VerificationCode.route){
            VerificationCodeScreen(
                openContent = {
                    appNavController.navigate(MainRouting.route) {
                        popUpTo(StartRouting.SignIn.route) { inclusive = true }
                    }
                },
                back = {
                    appNavController.popBackStack()
                }
            )
        }

    }
}