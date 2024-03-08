package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import javax.inject.Singleton

@Singleton
data class PlayWorkoutScreenState(
    val training: Training? = null,
    val statesWorkout: MutableState<List<StateWorkOut>> = mutableStateOf(emptyList()),
    val switchStartStop: MutableState<Boolean> = mutableStateOf(true),
    val tickTime: TickTime = TickTime(hour = "00", min="00", sec= "00"),
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = {},
    val onStart: ()->Unit = {},
    val onPause: ()->Unit = {},
    val onStop: ()->Unit = {},
    @Stable var onBaskScreen: () ->Unit = {},
    @Stable var startTime: Long = 0L,
)
