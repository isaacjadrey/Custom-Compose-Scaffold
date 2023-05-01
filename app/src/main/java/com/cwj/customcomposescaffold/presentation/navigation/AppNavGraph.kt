package com.cwj.customcomposescaffold.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cwj.composecustomscaffolddrawerlib.presentation.navigation.Screen
import com.cwj.composecustomscaffolddrawerlib.presentation.screens.CartScreen
import com.cwj.composecustomscaffolddrawerlib.presentation.screens.HomeScreen
import com.cwj.composecustomscaffolddrawerlib.presentation.screens.NotificationsScreen
import com.cwj.composecustomscaffolddrawerlib.presentation.screens.OrdersScreen
import com.cwj.composecustomscaffolddrawerlib.presentation.screens.ProfileScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route, builder = {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Notifications.route) { NotificationsScreen() }
        composable(Screen.Cart.route) { CartScreen() }
        composable(Screen.Orders.route) { OrdersScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    })
}