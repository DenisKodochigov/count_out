package com.example.count_out.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.count_out.entity.Const.defaultScreen

@Composable
fun NavHostApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = defaultScreen,
        modifier = modifier
    ){
        trainings( goToScreenTraining = { navController.navigateToTraining(it) },
            goToScreenPlayWorkOut = { navController.navigateToPlayWorkout(it) } )
        training ( onBaskScreen = { navController.popBackStack() })
        playWorkout ( onBaskScreen = { navController.popBackStack() })
        settings( onBaskScreen = { navController.popBackStack() })
    }
}

