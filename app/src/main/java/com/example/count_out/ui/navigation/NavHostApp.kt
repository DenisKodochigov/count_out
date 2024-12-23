package com.example.count_out.ui.navigation

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
        trainings( goToScreenTraining = { navController.navigateToTraining(it) },
            goToScreenPlayWorkOut = { navController.navigateToPlayWorkout(it) } )
        training ( onBaskScreen = { navController.popBackStack() })
        history()
        playWorkout ()
        settings()
    }
}

