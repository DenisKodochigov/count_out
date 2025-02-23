package com.count_out.services.service_logging

import com.count_out.data.entity.WorkoutRecord
import com.count_out.data.models.RunningState
import com.count_out.data.router.models.TemporaryBase
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Singleton
class Logging @Inject constructor() {
//    val dataRepository: DataRepository? = null
    val fff = RunningState.Stopped
    private val stateDouble: MutableStateFlow<RunningState> = MutableStateFlow(RunningState.Stopped)
    val latitude: Float = 0f
    val longitude: Float = 0f
    fun runLogging(dataForBase: MutableStateFlow<TemporaryBase?>, state: MutableStateFlow<RunningState?>){
        stateDouble.value = RunningState.Started
        CoroutineScope(Dispatchers.Default).launch {
            while (state.value != RunningState.Stopped && state.value != RunningState.Stopped){
                dataForBase.collect{ data ->
//                    lg("write $data")
                    data?.let {
//                      dataRepository.writeTemporaryData( it )
                    } } }
        }
    }
    fun stop(){ stateDouble.value = RunningState.Stopped}

    fun saveTraining(workout: WorkoutRecord){
        CoroutineScope(Dispatchers.Default).launch {
//            dataRepository.saveTraining(workout)
        }
    }
    fun notSaveTraining(){
//        dataRepository.clearTemporaryData()
    }
}