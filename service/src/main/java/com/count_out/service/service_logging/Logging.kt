package com.count_out.service.service_logging

import com.count_out.data.entity.WorkoutRecord
import com.count_out.data.router.models.TemporaryBase
import com.count_out.domain.entity.enums.RunningState
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Singleton
class Logging @Inject constructor() {
//    val dataRepository: DataRepository? = null
    val fff = com.count_out.domain.entity.enums.RunningState.Stopped
    private val stateDouble: MutableStateFlow<com.count_out.domain.entity.enums.RunningState> = MutableStateFlow(
        com.count_out.domain.entity.enums.RunningState.Stopped
    )
    val latitude: Float = 0f
    val longitude: Float = 0f
    fun runLogging(dataForBase: MutableStateFlow<TemporaryBase?>, state: MutableStateFlow<com.count_out.domain.entity.enums.RunningState?>){
        stateDouble.value = com.count_out.domain.entity.enums.RunningState.Started
        CoroutineScope(Dispatchers.Default).launch {
            while (state.value != com.count_out.domain.entity.enums.RunningState.Stopped && state.value != com.count_out.domain.entity.enums.RunningState.Stopped){
                dataForBase.collect{ data ->
//                    lg("write $data")
                    data?.let {
//                      dataRepository.writeTemporaryData( it )
                    } } }
        }
    }
    fun stop(){ stateDouble.value = com.count_out.domain.entity.enums.RunningState.Stopped
    }

    fun saveTraining(workout: WorkoutRecord){
        CoroutineScope(Dispatchers.Default).launch {
//            dataRepository.saveTraining(workout)
        }
    }
    fun notSaveTraining(){
//        dataRepository.clearTemporaryData()
    }
}