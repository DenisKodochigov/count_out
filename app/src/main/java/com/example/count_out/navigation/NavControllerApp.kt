package com.example.basket.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.count_out.navigation.RoundDestination

fun NavHostController.navigateToScreen(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(this@navigateToScreen.graph.findStartDestination().id) {
            saveState = true
        }
    }

fun NavHostController.navigateToProducts(basketId: Long) {
    this.navigateToScreen("${RoundDestination.route}/$basketId")
}





