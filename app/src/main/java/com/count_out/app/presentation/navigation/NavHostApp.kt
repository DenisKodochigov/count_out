package com.count_out.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.count_out.app.presentation.Const.DEFAULT_SCREEN

@Composable
fun NavHostApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = DEFAULT_SCREEN.route,
        modifier = modifier
    ){
        val navEvent = NavigateEventImpl(navController)
        executeWorkout( navigateEvent = navEvent)
        plans( navigateEvent = navEvent)
        plan( navigateEvent = navEvent)
        history(navigateEvent = navEvent)
        settings(navigateEvent = navEvent)
    }
}

