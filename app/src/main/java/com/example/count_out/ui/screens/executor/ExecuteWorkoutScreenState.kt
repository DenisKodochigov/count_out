package com.example.count_out.ui.screens.executor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.TickTime
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.ui.ExecuteInfoExercise
import com.example.count_out.entity.ui.ExecuteInfoSet
import com.example.count_out.entity.workout.Coordinate
import com.example.count_out.entity.workout.Training
import javax.inject.Singleton

@Singleton
data class ExecuteWorkoutScreenState(
    val training: Training? = null,

    val flowTime: TickTime = TickTime(hour = "00", min="00", sec= "00"),
    val countRest: Int = 0,
    val countReps: Int = 0,
//    val currentSet: SetDB? = null,
    val currentCount: Int = 0,
    val currentDuration: Int = 0,
    val currentDistance: Int = 0,
    val enableChangeInterval: Boolean = false,
    val executeInfoExercise: ExecuteInfoExercise? = null,
    val executeInfoSet: ExecuteInfoSet? = null,

    val heartRate: Int = 0,
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val bleConnectState: ConnectState = ConnectState.NOT_CONNECTED,

    val coordinate: Coordinate? = null,

    val showBottomSheetSaveTraining: MutableState<Boolean> = mutableStateOf(false),
    val stateWorkOutService: RunningState? = null,
    val updateSet: (Long, SetDB)->Unit = { _, _->},
    val startWorkOutService: (Training)->Unit = {},
    val stopWorkOutService: ()->Unit = {},
    val pauseWorkOutService: ()->Unit = { },
    val saveTraining: ()->Unit = { },
    val notSaveTraining: ()->Unit = { },
    @Stable var startTime: Long = 0L,

    @Stable var onDismissSaveTraining: (ExecuteWorkoutScreenState) -> Unit = { uiState ->
        uiState.showBottomSheetSaveTraining.value = false
        notSaveTraining()
    },
    @Stable var onConfirmASaveTraining: (ExecuteWorkoutScreenState) -> Unit = { uiState ->
        uiState.showBottomSheetSaveTraining.value = false
        saveTraining()
    },
){
//    fun getExecuteSetInfo(training: Training, setId: Long): ExecuteSetInfo {
//
//        var setInfo = ExecuteSetInfo()
//        var flag = false
//        training.rounds.forEach{ round->
//            round.exercise.forEach{ exercise->
//                if (flag) {
//                    lg("getExecuteSetInfo nextActivityName:${exercise.activity.name}")
//                    return setInfo.copy(
//                        nextActivityName = exercise.activity.name,
//                        nextExercise = exercise.idExercise)
//                }
//                exercise.sets.forEachIndexed{ ind, set->
//                    if ((!flag && setId == 0L) || (set.idSet == setId)){
//                        setInfo = ExecuteSetInfo(
//                            activityName = exercise.activity.name,
//                            countSet = exercise.sets.count(),
//                            currentIndexSet = ind + 1,
//                            countRing = round.countRing,)
//                        flag = true
//                    }
//                }
//            }
//        }
//        lg("getExecuteSetInfo setId:${setId}")
//        return setInfo
//    }
//    fun getBeginningSet(training: Training): Set? {
//        training.rounds.forEach{ round->
//            round.exercise.forEach{ exercise->
//                exercise.sets.forEach{ set-> return set }
//            }
//        }
//        return null
//    }
//    fun addMessage(messageWorkOut: MessageWorkOut):List<MessageWorkOut>{
//        return messageWorkout.toMutableList().apply { this.add(messageWorkOut) }
//    }
//    fun activityList(setId: Long = -1): List<ExecuteSetInfo>{
//
//        var findingSet = false
//        var currentSet = 0
//        val resultList: MutableList<ExecuteSetInfo> = mutableListOf()
//        if (setId > -1){
//            training?.let { trainingIt->
//                trainingIt.rounds.forEachIndexed { _, round ->
//                    if (findingSet) resultList.add(ExecuteSetInfo())
//                    round.exercise.forEachIndexed{ _, exercise ->
//                        exercise.sets.forEachIndexed{ indexSet, set ->
//                            if (findingSet){
//                                resultList.add(
//                                    ExecuteSetInfo(
//                                    activityName = exercise.activity.name,
//                                    typeDescription = true,
//                                    countSet = exercise.sets.count(),
//                                    currentIndexSet = currentSet,
//                                    countRing = 0,
//                                    currentRing = 0,
//                                )
//                                )
//                                currentSet = 0
//                            }
//                            if (set.idSet == setId) {
//                                findingSet = true
//                                currentSet = indexSet
//                                resultList.add(ExecuteSetInfo())
//                                resultList.add(
//                                    ExecuteSetInfo(
//                                    activityName = exercise.activity.name,
//                                    typeDescription = false,
//                                    countSet = exercise.sets.count(),
//                                    currentIndexSet = currentSet,
//                                    countRing = 0,
//                                    currentRing = 0,
//                                )
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            training?.let { trainingIt->
//                trainingIt.rounds.forEachIndexed { _, round ->
//                    resultList.add(ExecuteSetInfo())
//                    round.exercise.forEachIndexed{ _, exercise ->
//                        resultList.add(
//                            ExecuteSetInfo(
//                            activityName = exercise.activity.name,
//                            typeDescription = true,
//                            countSet = exercise.sets.count(),
//                            currentIndexSet = 0,
//                            countRing = 0,
//                            currentRing = 0,
//                        )
//                        )
//                    }
//                }
//            }
//        }
//        return resultList
//    }
}


