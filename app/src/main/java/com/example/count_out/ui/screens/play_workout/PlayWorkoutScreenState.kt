package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.Stable
import com.example.count_out.entity.Training
import javax.inject.Singleton

@Singleton
data class PlayWorkoutScreenState(
    val training: Training? = null,
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = {},
    val onStart: ()->Unit = {},
    val onPause: ()->Unit = {},
    val onStop: ()->Unit = {},
    @Stable var onBaskScreen: () ->Unit = {},
)