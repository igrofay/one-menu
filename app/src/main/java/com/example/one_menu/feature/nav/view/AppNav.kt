package com.example.one_menu.feature.nav.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.one_menu.feature.nav.model.MainRouting
import com.example.one_menu.feature.nav.model.StartRouting

@Composable
fun AppNav() {
    val appNavController = rememberNavController()
    NavHost(
        navController = appNavController,
        startDestination = StartRouting.route,
    ){
        startNav(appNavController)
        mainNav(appNavController)
    }
}