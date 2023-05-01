package com.cwj.composecustomscaffolddrawerlib.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingBasket
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String, val icon: ImageVector, val title: String) {
    object Home: Screen("home", Icons.Rounded.Home, "Home")
    object Notifications: Screen("notifications", Icons.Rounded.Notifications, "Notifications")
    object Cart: Screen("cart", Icons.Rounded.ShoppingCart, "Cart")
    object Orders: Screen("orders", Icons.Rounded.ShoppingBasket, "Orders")
    object Profile: Screen("profile", Icons.Rounded.Person, "Profile")
}