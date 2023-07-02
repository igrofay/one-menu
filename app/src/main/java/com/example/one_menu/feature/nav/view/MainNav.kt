package com.example.one_menu.feature.nav.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.one_menu.feature.menu.view.MenuScreen
import com.example.one_menu.feature.nav.model.BottomNavBarSetting
import com.example.one_menu.feature.nav.model.MainRouting
import com.example.one_menu.feature.profile.view.ProfileScreen
import com.example.one_menu.feature.support.view.SupportScreen
import kotlinx.coroutines.withContext

val LocalBottomNavBarSetting = compositionLocalOf<BottomNavBarSetting?> { null }

fun NavGraphBuilder.mainNav(
    appNavController: NavController,
) {
    composable(MainRouting.route) {
        val navController = rememberNavController()
        val bottomNavBarSetting = BottomNavBarSetting
            .rememberBottomNavBarSetting()
        val density = LocalDensity.current

        val padding by animateDpAsState(
            targetValue = if (bottomNavBarSetting.useSafePadding)
                bottomNavBarSetting.bottomPaddingValues
            else
                0.dp
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            CompositionLocalProvider(
                LocalBottomNavBarSetting provides bottomNavBarSetting
            ) {
                NavHost(
                    navController = navController,
                    startDestination = MainRouting.Menu.route,
                    modifier = Modifier.padding(bottom = padding)
                ) {
                    composable(MainRouting.Profile.route) {
                        ProfileScreen()
                    }
                    composable(MainRouting.Menu.route) {
                        MenuScreen()
                    }
                    composable(MainRouting.Support.route) {
                        SupportScreen {
                            navController.popBackStack()
                        }
                    }
                }
            }
            if (bottomNavBarSetting.visibleBottomBar)
                MainBottomNavBar(
                    routes = MainRouting.routes,
                    onClickItem = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.onGloballyPositioned {
                        bottomNavBarSetting.messageBottomPadding(with(density){ it.size.height.toDp() })
                    }
                )
        }
    }
}