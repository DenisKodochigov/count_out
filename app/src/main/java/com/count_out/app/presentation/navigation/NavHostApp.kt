package com.count_out.app.presentation.navigation

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
        val navEvent = NavigateEventImpl(navController)
        trainings( navigateEvent = navEvent)
        training(  navigateEvent = navEvent)
        history()
        playWorkout(navigateEvent = navEvent)
        settings()
    }
}

