package com.count_out.data.router

import com.count_out.data.router.models.BufferImpl
import com.count_out.data.router.models.DataForBle
import com.count_out.data.router.models.DataForNotification
import com.count_out.data.router.models.DataForSite
import com.count_out.data.router.models.DataForUI
import com.count_out.data.router.models.DataForWork
import com.count_out.data.router.models.DataFromBle
import com.count_out.data.router.models.DataFromSite
import com.count_out.data.router.models.DataFromWork
import com.count_out.data.router.models.TemporaryBase
import com.count_out.domain.entity.router.Buffer
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.router.DataForServ
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

    private fun bufferInit(dataFromBle: DataFromBle, dataFromWork: DataFromWork, dataFromSite: DataFromSite): Buffer {
        return BufferImpl(
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
            stepTraining = dataFromWork.stepTraining,
            durationSpeech = dataFromWork.durationSpeech,

            coordinate = dataFromSite.coordinate
        )
    }
    private fun initDataForWork(dataForServ: DataForServ): DataForWork {
        return DataForWork(
            training = dataForServ.training,
            indexSet = dataForServ.indexSet,
            indexRound = dataForServ.indexRound,
            indexExercise = dataForServ.indexExercise,
            idSetChangeInterval = dataForServ.idSetChangeInterval,
            interval = dataForServ.interval,
            enableSpeechDescription = dataForServ.enableSpeechDescription,
            dataFromWork = dataFromWork,
        )
    }
    private fun initDataForUI(buffer: Buffer): DataForUI {
        dataForWork.createMapTraining()
        dataForWork.initStepTraining()
        return DataForUI(
            runningState = buffer.runningState,
            currentCount = buffer.currentCount,
            currentDistance = buffer.currentDistance,
            currentDuration = buffer.currentDuration,
            stepTraining = buffer.stepTraining,
        )
    }
    private fun initDataForSite(): DataForSite {
        return DataForSite(site = "", state = MutableStateFlow(RunningState.Stopped))
    }
    private fun initDataForBle(dataForServ: DataForServ): DataForBle {
        return DataForBle(
            addressForSearch = dataForServ.addressForSearch,
            currentConnection = dataForServ.currentConnection
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
                    dataForBase.value = TemporaryBase(
                        latitude = buffer.coordinate.value?.latitude ?: 0.0,
                        longitude = buffer.coordinate.value?.longitude ?: 0.0,
                        altitude = buffer.coordinate.value?.altitude ?: 0.0,
                        timeLocation = buffer.coordinate.value?.timeLocation ?: 0,
                        accuracy = buffer.coordinate.value?.accuracy ?: 0f,
                        speed = buffer.coordinate.value?.speed ?: 0f,
                        heartRate = buffer.heartRate.value,
                        idTraining = dataForWork.training.value?.idTraining ?: 0,
                        phaseWorkout = buffer.phaseWorkout.value,
                        distance = TODO(),
                        idSet = TODO(),
                        activityId = TODO(),
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
    fun sendBleToUi(){
        CoroutineScope(Dispatchers.Default).launch {
            while (true){
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
                        hours = buffer.flowTime.value?.hour ?: "00",
                        minutes = buffer.flowTime.value?.min ?: "00",
                        seconds = buffer.flowTime.value?.sec ?: "00",
                        heartRate = buffer.heartRate.value,
                        enableLocation = (buffer.coordinate.value?.longitude ?: 0.0) > 0.0
                    )
                }
                delay(1000L)
            }
        }
    }
}
