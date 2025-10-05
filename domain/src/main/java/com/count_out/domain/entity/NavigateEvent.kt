package com.count_out.domain.entity

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

interface NavigateEvent {
    fun goToScreenPlan(id: Long)
    fun goToScreenExecuteWorkout()
    fun goToScreenPlans()
    fun backStack()
    fun backStackEntry(route: String): NavBackStackEntry
    fun getNavController(): NavHostController
}