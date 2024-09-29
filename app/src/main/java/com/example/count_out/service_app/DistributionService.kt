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
    private var bleService: BleService? = null
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

    fun startDistributionService(sendToServ: SendToService): SendToUI?{
        sendToService = sendToServ
        startForegroundService()
        return sendToUI
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

    fun startWork(){
        if (sendToUI == null) { sendToUI = SendToUI() }
        sendToUI?.let { sendUI->
            if (sendUI.runningState.value == RunningState.Stopped){
                messageApp.messageApi("Start WorkOut")
                sendUI.runningState.value = RunningState.Started
                sendUI.nextSet.value = sendToService?.getSet(0)
                work?.start(sendToUI, sendToService){ stopWork() }
            } //else if (it.runningState.value == RunningState.Paused) { continueWorkout() }
        }
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
            bleBind.bindServiceBle()
            bleBind.service.startService()
        }
        bleService = bleBind.service
    }
    private fun stopBleService(){
        sendToUI?.let { bleService?.stopService(it) }
        bleBind.unbindService()
    }

    suspend fun connectDevice(){
        if ( bleBind.isBound.value ) {
            sendToUI?.let { bleService?.stopScannerBLEDevices(it) }
            sendToUI?.let { sendToService?.let { it1 -> bleService?.connectDevice(it, it1) } }
        } else {
            startBleService()
            sendToUI?.let { sendToService?.let { it1 -> bleService?.connectDevice(it, it1) } }
        }
    }
    fun disconnectDevice() {
        if ( bleBind.isBound.value ) bleService?.disconnectDevice()
    }
    fun startScanningBle(){
        if ( bleBind.isBound.value ) {
            sendToUI?.let { bleService?.stopScannerBLEDevices(it) }
            sendToUI?.let { bleService?.startScannerBLEDevices(it) }
        }
    }
    fun stopScanningBle() {
        if ( bleBind.isBound.value ) sendToUI?.let { bleService?.stopScannerBLEDevices(it) }
    }
    fun clearCacheBLE() { bleService?.onClearCacheBLE()}

    fun receiveDataBle(){}

    fun onClearCacheBLE() { bleService?.onClearCacheBLE()}

    fun startLocation(){site?.start()}

    fun stopLocation(){site?.stop()}

    fun receiveDataLocation(){}

    fun sendDataUI(){}

    fun sendDataBase(){}

    fun getStateBle(){}

    fun getStateLocation(){}

}