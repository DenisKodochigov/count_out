package com.example.count_out.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = TemplatesDestination.route,
        modifier = modifier
    ){
        templates { navController.navigateToWorkout(it) }
        template { navController.navigateToWorkout(it) }
        workouts { navController.navigateToWorkout(it) }
        workout { navController.navigateToRound(it) }
        round { navController.navigateToSet(it) }
        set { navController.navigateToWorkout(it) }
        settings()
    }
}

