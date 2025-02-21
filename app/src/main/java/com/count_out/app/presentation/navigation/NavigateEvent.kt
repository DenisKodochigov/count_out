package com.count_out.app.presentation.navigation

interface NavigateEvent {
    fun goToScreenTraining(id: Long)
    fun goToScreenExecuteWorkout(id: Long)
    fun backStack()
}