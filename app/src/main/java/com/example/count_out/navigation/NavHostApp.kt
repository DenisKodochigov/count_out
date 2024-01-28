package com.example.count_out.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavHostApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = TrainingsDestination.route,
        modifier = modifier
    ){
        trainings { navController.navigateToTraining(it) } //navController.navigateToWorkout(it)
        training ( onBaskScreen = { navController.popBackStack() })
        settings( onBaskScreen = { navController.popBackStack() })
    }
}

