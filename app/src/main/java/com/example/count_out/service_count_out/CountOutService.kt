package com.example.count_out.service_count_out

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
import com.example.count_out.entity.Site
import com.example.count_out.entity.StateBleScanner
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service_count_out.bluetooth.Bluetooth
import com.example.count_out.service_count_out.workout.Work
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class CountOutService @Inject constructor(): Service() {

    val sendToUI: SendToUI = SendToUI()
    private var sendToService: SendToService? = null
    private val site: Site? = null
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var messageApp: MessageApp
    @Inject lateinit var ble: Bluetooth
    @Inject lateinit var work: Work

    inner class DistributionServiceBinder: Binder() { fun getService(): CountOutService = this@CountOutService }
    override fun onBind(p0: Intent?): IBinder = DistributionServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(NOTIFICATION_EXTRA)) {
            RunningState.Started.name -> goonWork()
            RunningState.Paused.name -> pauseWork()
            RunningState.Stopped.name -> stopWork()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun startCountOutService(sendToServ: SendToService): SendToUI?{
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
    fun stopCountOutService(){
        sendToService = null
        messageApp.messageApi("Stop Distribution Service")
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
    }

    fun startWork(){
        if (sendToUI.runningState.value == RunningState.Stopped){
            messageApp.messageApi("Start WorkOut")
            sendToUI.runningState.value = RunningState.Started
            sendToUI.nextSet.value = sendToService?.getSet(0)
            work.start(sendToUI, sendToService){ stopWork() }
        } //else if (it.runningState.value == RunningState.Paused) { continueWorkout() }
    }
    private fun stopWork(){
        messageApp.messageApi("Stop WorkOut")
        work.stop()
        sendToUI.cancel()
    }
    fun stopWorkSignal() {
        messageApp.messageApi("Command Stop WorkOut")
        sendToUI.runningState.value = RunningState.Stopped
    }
    fun pauseWork(){
        messageApp.messageApi("Command Pause WorkOut")
        sendToUI.runningState.value = RunningState.Paused
        notificationHelper.setContinueButton()
    }
    fun goonWork(){
        if (sendToUI.runningState.value == RunningState.Paused){
            sendToUI.runningState.value = RunningState.Started
            notificationHelper.setPauseButton()
        }
    }

    fun connectDevice(){
        if (ble.state.stateBleScanner == StateBleScanner.RUNNING) ble.stopScannerBLEDevices(sendToUI)
        sendToService?.let { it1 -> ble.connectDevice(sendToUI, it1) }
    }
    fun disconnectDevice() {
        ble.disconnectDevice()
    }
    fun startScanningBle(){
        CoroutineScope(Dispatchers.Default).launch {
            ble.stopScannerBLEDevices(sendToUI)
            ble.startScannerBLEDevices(sendToUI)
        }
    }
    fun stopScanningBle() {
        ble.stopScannerBLEDevices(sendToUI)
    }

    fun receiveDataBle(){}

    fun onClearCacheBLE() { ble.onClearCacheBLE()}

    fun startLocation(){site?.start()}

    fun stopLocation(){site?.stop()}

    fun receiveDataLocation(){}

    fun sendDataUI(){}

    fun sendDataBase(){}

    fun getStateBle(){}

    fun getStateLocation(){}

}