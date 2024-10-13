package com.example.count_out.entity.router

import com.example.count_out.data.room.tables.TemporaryDB
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.ui.DataForServ
import com.example.count_out.entity.ui.DataForUI
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
        return Buffer(
            heartRate = dataFromBle.heartRate,
            scannedBle = dataFromBle.scannedBle,
            foundDevices = dataFromBle.foundDevices,
            connectingState = dataFromBle.connectingState,
            lastConnectHearthRateDevice = dataFromBle.lastConnectHearthRateDevice,

            speakingSet = dataFromWork.speakingSet,
            nextSet = dataFromWork.nextSet,
            rest = dataFromWork.rest,
            activityId = dataFromWork.activityId,
            message = dataFromWork.message,
            flowTick = dataFromWork.flowTick,
            runningState = dataFromWork.runningState,
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
            speakingSet = buffer.speakingSet,
            runningState = buffer.runningState,
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
                        idSet = buffer.speakingSet.value?.idSet ?: 0,
                        rest = buffer.rest.value ?: 0,
                        activityId = buffer.activityId.value ?: 0,
                        runningSet = if (buffer.speakingSet.value != null) 1 else 0,
                    )
                }
                delay(500L)
            }
        }
    }
    private fun sendDataToUi(){
        CoroutineScope(Dispatchers.Default).launch {
            while (true){
                if (buffer.runningState.value == RunningState.Started) { dataForUI.setWork(buffer) }
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
                        hours = buffer.message.value?.tickTime?.hour ?: "00",
                        minutes = buffer.message.value?.tickTime?.min ?: "00",
                        seconds = buffer.message.value?.tickTime?.sec ?: "00",
                        heartRate = buffer.heartRate.value,
                        enableLocation = (buffer.coordinate.value?.longitude ?: 0.0) > 0.0
                    )
                }
                delay(1000L)
            }
        }
    }
}
