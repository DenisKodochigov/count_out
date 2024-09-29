package com.example.count_out.ui.screens.play_workout

import androidx.compose.runtime.Stable
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.ListActivityForPlayer
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.Set
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.Training
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.no_use.MessageWorkOut
import javax.inject.Singleton

@Singleton
data class PlayWorkoutScreenState(
    val training: Training? = null,
    val messageWorkout: List<MessageWorkOut> = emptyList(),
    val playerSet: Set? = null,
    val nextSet: Set? = null,
    var listActivity: List< ListActivityForPlayer> = emptyList(),
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val connectingState: ConnectState = ConnectState.NOT_CONNECTED,
    val heartRate: Int = 0,

    val tickTime: TickTime = TickTime(hour = "00", min="00", sec= "00"),
    val updateSet: (Long, SetDB)->Unit = { _, _->},
    val stateWorkOutService: RunningState = RunningState.Stopped,
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = {},
    @Stable var startTime: Long = 0L,
){
    fun addMessage(messageWorkOut: MessageWorkOut):List<MessageWorkOut>{
        return messageWorkout.toMutableList().apply { this.add(messageWorkOut) }
    }
    fun activityList(setId: Long = -1): List<ListActivityForPlayer>{

        var findingSet = false
        var currentSet = 0
        val resultList: MutableList<ListActivityForPlayer> = mutableListOf()
        if (setId > -1){
            training?.let { trainingIt->
                trainingIt.rounds.forEachIndexed { _, round ->
                    if (findingSet) resultList.add(ListActivityForPlayer(roundNameId = round.roundType.strId))
                    round.exercise.forEachIndexed{ _, exercise ->
                        exercise.sets.forEachIndexed{ indexSet, set ->
                            if (findingSet){
                                resultList.add(ListActivityForPlayer(
                                    roundNameId = round.roundType.strId,
                                    activityName = exercise.activity.name,
                                    typeDescription = true,
                                    countSet = exercise.sets.count(),
                                    currentIndSet = currentSet,
                                    countRing = 0,
                                    currentRing = 0,
                                ))
                                currentSet = 0
                            }
                            if (set.idSet == setId) {
                                findingSet = true
                                currentSet = indexSet
                                resultList.add(ListActivityForPlayer(roundNameId = round.roundType.strId))
                                resultList.add(ListActivityForPlayer(
                                    roundNameId =round.roundType.strId,
                                    activityName = exercise.activity.name,
                                    typeDescription = false,
                                    countSet = exercise.sets.count(),
                                    currentIndSet = currentSet,
                                    countRing = 0,
                                    currentRing = 0,
                                ))
                            }
                        }
                    }
                }
            }
        } else {
            training?.let { trainingIt->
                trainingIt.rounds.forEachIndexed { _, round ->
                    resultList.add(ListActivityForPlayer(roundNameId = round.roundType.strId))
                    round.exercise.forEachIndexed{ _, exercise ->
                        resultList.add(ListActivityForPlayer(
                            roundNameId =round.roundType.strId,
                            activityName = exercise.activity.name,
                            typeDescription = true,
                            countSet = exercise.sets.count(),
                            currentIndSet = 0,
                            countRing = 0,
                            currentRing = 0,
                        ))
                    }
                }
            }
        }
        
        return resultList
    }
}
