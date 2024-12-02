package com.example.count_out.entity.router

import com.example.count_out.data.room.tables.TemporaryDB
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.ui.DataForServ
import com.example.count_out.entity.ui.DataForUI
import com.example.count_out.entity.ui.ExecuteSetInfo
import com.example.count_out.entity.workout.TemporaryBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Router(private val dataForServ: DataForServ) {

    val dataFromBle = DataFromBle()
    val dataFromSite = DataFromSite()
    val dataFromWork = DataFromWork()

    val dataForBle: DataForBle by lazy { initDataForBle(dataForServ) }
    val dataForWork: DataForWork by lazy {initDataForWork(dataForServ)}
    val dataForSite: DataForSite by lazy { initDataForSite() }

    private val buffer: Buffer by lazy { bufferInit(dataFromBle, dataFromWork, dataFromSite )}
    val dataForUI: DataForUI by lazy { initDataForUI(buffer) }
    val dataForNotification: MutableStateFlow<DataForNotification?> = MutableStateFlow(null)
    val dataForBase: MutableStateFlow<TemporaryBase?> = MutableStateFlow(null)

    private fun bufferInit(dataFromBle: DataFromBle, dataFromWork: DataFromWork, dataFromSite: DataFromSite): Buffer{

        dataFromWork.exerciseInfo.value = ExecuteSetInfo(
            activityName = dataForWork.getExercise()?.activity?.name ?: "",
            activityId = dataForWork.getExercise()?.activity?.idActivity ?: 0,
            countSet = dataForWork.getExercise()?.sets?.count() ?: 0,
            currentSet = dataForWork.getSet(),
            currentIndexSet = dataForWork.indexSet,
            nextExercise = dataForWork.getNextExercise()?.idExercise ?: 0,
            nextActivityName = dataForWork.getNextExercise()?.activity?.name ?: "",
            nextExerciseSummarizeSet = dataForWork.getSummarizeSet()
        )
        return Buffer(
            heartRate = dataFromBle.heartRate,
            scannedBle = dataFromBle.scannedBle,
            foundDevices = dataFromBle.foundDevices,
            bleConnectState = dataFromBle.connectingState,
            lastConnectHearthRateDevice = dataFromBle.lastConnectHearthRateDevice,

            runningState = dataFromWork.runningState,
            flowTime = dataFromWork.flowTime,
            countRest = dataFromWork.countRest,
            currentCount = dataFromWork.currentCount,
            currentDuration = dataFromWork.currentDuration,
            currentDistance = dataFromWork.currentDistance,
            enableChangeInterval = dataFromWork.enableChangeInterval,
            exerciseInfo = dataFromWork.exerciseInfo,
            durationSpeech = dataFromWork.durationSpeech,

            coordinate = dataFromSite.coordinate
        )
    }
    private fun initDataForBle(dataForServ: DataForServ):DataForBle{
        return DataForBle(
            addressForSearch = dataForServ.addressForSearch,
            currentConnection = dataForServ.currentConnection
        )
    }
    private fun initDataForWork(dataForServ: DataForServ): DataForWork{
        return DataForWork(
            training = dataForServ.training,
            indexSet = dataForServ.indexSet,
            indexRound = dataForServ.indexRound,
            indexExercise = dataForServ.indexExercise,
            enableSpeechDescription = dataForServ.enableSpeechDescription
        )
    }
    private fun initDataForSite(): DataForSite{
        return DataForSite(site = "", state = MutableStateFlow(RunningState.Stopped))
    }
    private fun initDataForUI(buffer: Buffer): DataForUI {
        return DataForUI(
            runningState = buffer.runningState,
            exerciseInfo = buffer.exerciseInfo
        )
    }
    fun sendData(){
        sendDataToBase()
        sendDataToUi()
        sendDataToNotification()
    }

    private fun sendDataToBase(){
        CoroutineScope(Dispatchers.Default).launch {
            while (true){
                if (buffer.runningState.value == RunningState.Started) {
                    dataForBase.value = TemporaryDB(
                        latitude = buffer.coordinate.value?.latitude ?: 0.0,
                        longitude = buffer.coordinate.value?.longitude ?: 0.0,
                        altitude = buffer.coordinate.value?.altitude ?: 0.0,
                        timeLocation = buffer.coordinate.value?.timeLocation ?: 0,
                        accuracy = buffer.coordinate.value?.accuracy ?: 0f,
                        speed = buffer.coordinate.value?.speed ?: 0f,
                        heartRate = buffer.heartRate.value,
                        idTraining = dataForWork.training.value?.idTraining ?: 0,
                        idSet = buffer.exerciseInfo.value?.currentSet?.idSet ?: 0,
                        phaseWorkout = buffer.phaseWorkout.value,
                        activityId = buffer.exerciseInfo.value?.activityId ?: 0,
                    )
                }
                delay(500L)
            }
        }
    }
    private fun sendDataToUi(){
        CoroutineScope(Dispatchers.Default).launch {
            while (true){
                if (buffer.runningState.value == RunningState.Started) {
                    dataForUI.setWork(buffer) }
                dataForUI.setBle(buffer)
                delay(1000L)
            }
        }
    }
    private fun sendDataToNotification(){
        CoroutineScope(Dispatchers.Default).launch {
            while (true){
                if (buffer.runningState.value == RunningState.Started) {
                    dataForNotification.value = DataForNotification(
                        hours = buffer.flowTime.value.hour,
                        minutes = buffer.flowTime.value.min,
                        seconds = buffer.flowTime.value.sec,
                        heartRate = buffer.heartRate.value,
                        enableLocation = (buffer.coordinate.value?.longitude ?: 0.0) > 0.0
                    )
                }
                delay(1000L)
            }
        }
    }
}
