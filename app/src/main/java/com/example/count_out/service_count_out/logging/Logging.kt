package com.example.count_out.service_count_out.logging

import com.example.count_out.data.DataRepository
import com.example.count_out.data.room.tables.WorkoutDB
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.workout.TemporaryBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Logging @Inject constructor(val dataRepository: DataRepository) {

    private val stateDouble: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped)
    val latitude: Float = 0f
    val longitude: Float = 0f
    fun runLogging(dataForBase: MutableStateFlow<TemporaryBase?>, state: MutableStateFlow<RunningState?>){
        stateDouble.value = RunningState.Started
        CoroutineScope(Dispatchers.Default).launch {
            while (state.value != RunningState.Stopped && state.value != RunningState.Stopped){
                dataForBase.collect{ data ->
//                    lg("write $data")
                    data?.let { dataRepository.writeTemporaryData( it ) } } }
        }
    }
    fun stop(){ stateDouble.value = RunningState.Stopped}

    fun saveTraining(workout: WorkoutDB){
        CoroutineScope(Dispatchers.Default).launch {
            dataRepository.saveTraining(workout)
        }
    }
    fun notSaveTraining(){
        dataRepository.clearTemporaryData()
    }
}