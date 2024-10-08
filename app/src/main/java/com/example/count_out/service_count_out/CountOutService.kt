package com.example.count_out.service_count_out

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.R
import com.example.count_out.entity.CommandService
import com.example.count_out.entity.Const.NOTIFICATION_EXTRA
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.DataForServ
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.Router
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service_count_out.bluetooth.Bluetooth
import com.example.count_out.service_count_out.location.Site
import com.example.count_out.service_count_out.work.Work
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class CountOutService @Inject constructor(): Service() {

    private lateinit var router: Router
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var messageApp: MessageApp
    @Inject lateinit var ble: Bluetooth
    @Inject lateinit var work: Work
    @Inject lateinit var site: Site

    inner class DistributionServiceBinder: Binder() { fun getService(): CountOutService = this@CountOutService }
    override fun onBind(p0: Intent?): IBinder = DistributionServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(NOTIFICATION_EXTRA)) {
            RunningState.Started.name -> startWork()
            RunningState.Paused.name -> pauseWork()
            RunningState.Stopped.name -> router.dataForUI.runningState.value = RunningState.Stopped
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun commandService(command: CommandService, dataServ: DataForServ){
        when(command){
            CommandService.START_WORK->{ startWork() }
            CommandService.STOP_WORK->{ stopWork() }
            CommandService.PAUSE_WORK->{ pauseWork() }
            CommandService.CONNECT_DEVICE->{ble.connectDevice( router.dataFromBle, router.dataForBle ) }
            CommandService.DISCONNECT_DEVICE->{ ble.disconnectDevice() }
            CommandService.START_SCANNING->{ ble.startScanning(router.dataFromBle) }
            CommandService.STOP_SCANNING->{ ble.stopScanning(router.dataFromBle) }
            CommandService.CLEAR_CACHE_BLE->{ ble.onClearCacheBLE() }
            CommandService.START_LOCATION->{ }
            CommandService.STOP_LOCATION->{ }
        }
    }

    fun startCountOutService(dataForServ: DataForServ, callBack: ()->Unit): DataForUI{
        router = Router(dataForServ)
        startForegroundService()
        notificationUpdate()
        callBack()
        startSite()
        router.sendDataToUi()
        router.sendDataToNotification()
        return router.dataForUI
    }
    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31) {
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
    fun stopCountOutService(){
        messageApp.messageApi(R.string.stop_distribution_service)
        stopSite()
        notificationHelper.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }
    private fun startSite(){
        site.start(router.dataForSite, router.dataFromSite)
        router.dataForSite.state.value = RunningState.Started }
    private fun stopSite(){
        site.stop()
        router.dataForSite.state.value = RunningState.Stopped }
    private fun startWork(){
        if (router.dataForUI.runningState.value == RunningState.Paused){
            router.dataFromWork.runningState.value = RunningState.Started
            notificationHelper.updateNotification(router.dataForNotification.value, router.dataFromWork.runningState.value)
        }
        if (router.dataForUI.runningState.value == RunningState.Stopped){
            router.dataFromWork.runningState.value = RunningState.Started
            notificationHelper.updateNotification(router.dataForNotification.value, router.dataFromWork.runningState.value)
            router.dataFromWork.nextSet.value = router.dataForWork.getSet(0)
            lg("#################### Start Service Work ##########################")
            work.start( router.dataForWork, router.dataFromWork )
        }
    }
    private fun stopWork(){
        lg("#################### Stop Service Work ##########################")
        router.dataFromWork.runningState.value = RunningState.Stopped
    }
    private fun notificationUpdate(){
        CoroutineScope(Dispatchers.Default).launch {
            router.dataForNotification.collect{
                notificationHelper.updateNotification( it, router.dataFromWork.runningState.value )
            }
        }
    }
    private fun pauseWork(){
        lg("Pause Work")
        router.dataFromWork.runningState.value = RunningState.Paused
        notificationHelper.updateNotification(router.dataForNotification.value, router.dataFromWork.runningState.value)
//        notificationHelper.setContinueButton()
    }
}