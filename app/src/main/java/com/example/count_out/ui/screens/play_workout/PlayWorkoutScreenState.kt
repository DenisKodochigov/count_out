package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.Set
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.entity.no_use.MessageWorkOut
import javax.inject.Singleton

@Singleton
data class PlayWorkoutScreenState(
    val training: Training? = null,
    val statesWorkout: MutableState<List<MessageWorkOut>> = mutableStateOf(emptyList()),
    val switchState: MutableState<StateRunning> = mutableStateOf( StateRunning.Stopped),
    val playerSet: MutableState<Set> = mutableStateOf(SetDB() as Set),
    val tickTime: TickTime = TickTime(hour = "00", min="00", sec= "00"),
    val updateSet: (Long, SetDB)->Unit = { _, _->},
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = {},
    @Stable var onBaskScreen: () ->Unit = {},
    @Stable var startTime: Long = 0L,
){
    fun findSet(setId: Long): Set?{
        return training?.let { trainingIt->
            trainingIt.rounds.forEachIndexed { indexRound, round ->
                round.exercise.forEachIndexed{ indexExercise, exercise ->
                    exercise.sets.forEachIndexed{ indexSet, set ->
                        if (set.idSet == setId) {
                            return@let training.rounds[indexRound].exercise[indexExercise].sets[indexSet]
                        }
                    }
                }
            }
            null
        }
    }
}
//switchStartPause