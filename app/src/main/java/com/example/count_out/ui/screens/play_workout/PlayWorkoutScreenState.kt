package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.Stable
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.ListActivityForPlayer
import com.example.count_out.entity.Set
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.no_use.MessageWorkOut
import javax.inject.Singleton

@Singleton
data class PlayWorkoutScreenState(
    val training: Training? = null,
    val statesWorkout: List<MessageWorkOut> = emptyList(),
    val switchState: StateRunning = StateRunning.Stopped,
    val playerSet: Set? = null,
    var listActivity: List< ListActivityForPlayer> = emptyList(),
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val connectingDevice: ConnectState = ConnectState.NOT_CONNECTED,
    val heartRate: Int = 0,

    val tickTime: TickTime = TickTime(hour = "00", min="00", sec= "00"),
    val updateSet: (Long, SetDB)->Unit = { _, _->},
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = {},
    @Stable var startTime: Long = 0L,
){

    fun activityList(setId: Long = -1): List<ListActivityForPlayer>{

        var findingSet = false
        var currentSet = 0
        val resultList: MutableList<ListActivityForPlayer> = mutableListOf()
        if (setId > -1){
            training?.let { trainingIt->
                trainingIt.rounds.forEachIndexed { _, round ->
                    round.exercise.forEachIndexed{ _, exercise ->
                        exercise.sets.forEachIndexed{ indexSet, set ->
                            if (set.idSet == setId) {
                                findingSet = true
                                currentSet = indexSet
                            }
                        }
                        if (findingSet){
                            resultList.add(ListActivityForPlayer(
                                roundNameId =round.roundType.strId,
                                activityName = exercise.activity.name,
                                countSet = exercise.sets.count(),
                                currentSet = currentSet,
                                countRing = 0,
                                currentRing = 0,
                            ))
                            currentSet = 0
                        }
                    }
                }
            }
        } else {
            training?.let { trainingIt->
                trainingIt.rounds.forEachIndexed { indexRound, round ->
                    round.exercise.forEachIndexed{ indexExercise, exercise ->
                        resultList.add(ListActivityForPlayer(
                            roundNameId =round.roundType.strId,
                            activityName = exercise.activity.name,
                            countSet = exercise.sets.count(),
                            currentSet = 0,
                            countRing = 0,
                            currentRing = 0,
                        ))
                    }
                }
            }
        }
        
        return resultList
    }
//    fun findSet(setId: Long = 0): Set?{
//        if (playerSet.idSet > -1 || setId > -1){
//            return training?.let { trainingIt->
//                trainingIt.rounds.forEachIndexed { indexRound, round ->
//                    round.exercise.forEachIndexed{ indexExercise, exercise ->
//                        exercise.sets.forEachIndexed{ indexSet, set ->
//                            if (set.idSet == setId) {
//                                return@let training.rounds[indexRound].exercise[indexExercise].sets[indexSet]
//                            }
//                        }
//                    }
//                }
//                null
//            }
//        } else return null
//    }
//    fun findExercise(setId: Long = 0): Exercise?{
//        if (playerSet.idSet > -1 || setId > -1){
//            return training?.let { trainingIt->
//                trainingIt.rounds.forEachIndexed { indexRound, round ->
//                    round.exercise.forEachIndexed{ indexExercise, exercise ->
//                        exercise.sets.forEachIndexed{ indexSet, set ->
//                            if (set.idSet == setId) {
//                                return@let training.rounds[indexRound].exercise[indexExercise]
//                            }
//                        }
//                    }
//                }
//                null
//            }
//        } else return null
//    }
//    fun findNext(setId: Long = 0): TrainingMap?{
//        var findingSet = false
//        if (playerSet.idSet > -1 || setId > -1){
//            return training?.let { trainingIt->
//                trainingIt.rounds.forEachIndexed { indexRound, round ->
//                    round.exercise.forEachIndexed{ indexExercise, exercise ->
//                        exercise.sets.forEachIndexed{ indexSet, set ->
//                            if (findingSet){
//                                return@let TrainingMap(idRound = indexRound, idExercise = indexExercise, idSet = indexSet)
//                            }
//                            if (set.idSet == setId) findingSet = true
//                        }
//                    }
//                }
//                null
//            }
//        } else return null
//    }
//    fun countSet(setId: Long = 0): Int?{
//        if (playerSet.idSet > -1 || setId > -1){
//            return training?.let { trainingIt->
//                trainingIt.rounds.forEachIndexed { indexRound, round ->
//                    round.exercise.forEachIndexed{ indexExercise, exercise ->
//                        exercise.sets.forEachIndexed{ indexSet, set ->
//                            if (set.idSet == setId) exercise.sets.count()
//                        }
//                    }
//                }
//                null
//            }
//        } else return null
//    }
}
