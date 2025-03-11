package com.count_out.domain.entity

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

interface NavigateEvent {
    fun goToScreenTraining(id: Long)
    fun goToScreenExecuteWorkout(id: Long)
    fun backStack()
    fun backStackEntry(route: String): NavBackStackEntry
    fun getNavController(): NavHostController
}