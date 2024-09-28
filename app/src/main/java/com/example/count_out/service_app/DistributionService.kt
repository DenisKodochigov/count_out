package com.example.count_out.service_app

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.entity.Const.NOTIFICATION_EXTRA
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.bluetooth.BleBind
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class DistributionService @Inject constructor(): Service() {

    val currentData: MutableList<CurrentData>? = null
    val stateBle: BleStates? = null
    val stateTraining: TrainingState? = null
    var sendToUI: SendToUI? = null
    private var sendToService: SendToService? = null
    private var ble: Ble? = null
    private val work: Work? = null
    private val site: Site? = null
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var messageApp: MessageApp
    @Inject lateinit var bleBind: BleBind

    inner class DistributionServiceBinder: Binder() { fun getService(): DistributionService = this@DistributionService }
    override fun onBind(p0: Intent?): IBinder = DistributionServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(NOTIFICATION_EXTRA)) {
            RunningState.Started.name -> goonWork()
            RunningState.Paused.name -> pauseWork()
            RunningState.Stopped.name -> stopWork()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    suspend fun startDistributionService(sendToServ: SendToService){
        sendToService = sendToServ
        startForegroundService()
        startBleService()
    }
    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31) {
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
    fun stopDistributionService(){
        messageApp.messageApi("Stop WorkOut")
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        stopBleService()
    }

    fun startWork(): SendToUI?{
        if (sendToUI == null) { sendToUI = SendToUI() }
        sendToUI?.let {
            if (it.runningState.value == RunningState.Stopped){
                messageApp.messageApi("Start WorkOut")
                it.runningState.value = RunningState.Started
                it.nextSet.value = sendToService?.getSet(0)
                work?.start(sendToUI, sendToService){ stopWork() }
            } //else if (it.runningState.value == RunningState.Paused) { continueWorkout() }
        }
        return sendToUI
    }
    private fun stopWork(){
        work?.stop()
        messageApp.messageApi("Stop WorkOut")
        sendToUI?.cancel()
        sendToUI = null
        sendToService = null
    }
    fun stopWorkSignal() {
        messageApp.messageApi("Command Stop WorkOut")
        sendToUI?.let { it.runningState.value = RunningState.Stopped }
    }
    fun pauseWork(){
        sendToUI?.let { it.runningState.value = RunningState.Paused }
        notificationHelper.setContinueButton()
    }
    fun goonWork(){
        sendToUI?.let {
            if (it.runningState.value == RunningState.Paused){
                sendToUI?.let { it.runningState.value = RunningState.Started }
                notificationHelper.setPauseButton()
            }
        }
    }

    private suspend fun startBleService(){
        if ( !bleBind.isBound.value ) {
            bleBind.bindService()
            bleBind.service.startService()
        }
        ble = bleBind.service
    }
    private fun stopBleService(){
        sendToUI?.let { ble?.stopService(it) }
        bleBind.unbindService()
    }

    fun connectDevice(){
        if ( bleBind.isBound.value ) {
            sendToUI?.let { ble?.stopScannerBLEDevices(it) }
            sendToUI?.let { sendToService?.let { it1 -> ble?.connectDevice(it, it1) } }
        }
    }
    fun disconnectDevice() {
        if ( bleBind.isBound.value ) ble?.disconnectDevice()
    }
    fun startScanningBle(){
        if ( bleBind.isBound.value ) {
            sendToUI?.let { ble?.stopScannerBLEDevices(it) }
            sendToUI?.let { ble?.startScannerBLEDevices(it) }
        }
    }
    fun stopScanningBle() {
        if ( bleBind.isBound.value ) sendToUI?.let { ble?.stopScannerBLEDevices(it) }
    }
    fun clearCacheBLE() { ble?.onClearCacheBLE()}

    fun receiveDataBle(){}

    fun onClearCacheBLE() { ble?.onClearCacheBLE()}

    fun startLocation(){site?.start()}

    fun stopLocation(){site?.stop()}

    fun receiveDataLocation(){}

    fun sendDataUI(){}

    fun sendDataBase(){}

    fun getStateBle(){}

    fun getStateLocation(){}

}