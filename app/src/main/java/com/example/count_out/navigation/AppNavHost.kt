package com.example.count_out.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.basket.navigation.navigateToProducts

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = WorkoutsDestination.route,
        modifier = modifier
    ){
        workouts { navController.navigateToProducts(it) }
        workout { navController.navigateToProducts(it) }
        round { navController.navigateToProducts(it) }
        set { navController.navigateToProducts(it) }
        settings()
    }
}

