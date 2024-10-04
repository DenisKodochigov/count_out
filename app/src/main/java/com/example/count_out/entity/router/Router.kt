package com.example.count_out.entity.router

import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Router(private val dataForServ: DataForServ) {

    val dataForUI = DataForUI()
    val dataForBase = DataForBase()

    val dataFromBle = DataFromBle()
    val dataFromSite = DataFromSite()
    val dataFromWork = DataFromWork()

    val dataForBle: DataForBle by lazy { initDataForBle(dataForServ) }
    val dataForWork: DataForWork by lazy {initDataForWork(dataForServ)}
    val dataForSite: DataForSite by lazy { initDataForSite(dataForServ) }

    val buffer: Buffer by lazy { bufferInit(dataFromBle, dataFromWork, dataFromSite )}

    fun initRouter(){}
    fun startRouter(){}
    fun stopRouter(){}

    private fun bufferInit(dataFromBle: DataFromBle, dataFromWork: DataFromWork, dataFromSite: DataFromSite): Buffer{
        return Buffer(
            heartRate = dataFromBle.heartRate,
            scannedBle = dataFromBle.scannedBle,
            foundDevices = dataFromBle.foundDevices,
            connectingState = dataFromBle.connectingState,
            lastConnectHearthRateDevice = dataFromBle.lastConnectHearthRateDevice,

            set = dataFromWork.set,
            mark = dataFromWork.mark,
            nextSet = dataFromWork.nextSet,
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
            runningState = dataFromWork.runningState,
            enableSpeechDescription = dataForServ.enableSpeechDescription
        )
    }
    private fun initDataForSite(dataForServ: DataForServ): DataForSite{
        return DataForSite(site = "")
    }
    private fun receiveData(dataFromBle: DataFromBle, dataFromWork: DataFromWork, dataFromSite: DataFromSite){
        CoroutineScope(Dispatchers.Default).launch {
            dataFromBle.scannedBle.collect{ buffer.scannedBle.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromBle.heartRate.collect{ buffer.heartRate.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromBle.connectingState.collect{ buffer.connectingState.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromBle.foundDevices.collect{ buffer.foundDevices.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromBle.lastConnectHearthRateDevice.collect{ buffer.lastConnectHearthRateDevice.value = it }}

        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.flowTick.collect{ buffer.flowTick.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.message.collect{ buffer.message.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.set.collect{ buffer.set.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.nextSet.collect{ buffer.nextSet.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.durationSpeech.collect{ buffer.durationSpeech.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.runningState.collect{ buffer.runningState.value = it }}
        CoroutineScope(Dispatchers.Default).launch {
            dataFromWork.mark.collect{ buffer.mark.value = it }}

        CoroutineScope(Dispatchers.Default).launch {
            dataFromSite.coordinate?.collect{ buffer.coordinate?.value = it }
        }
    }
}
