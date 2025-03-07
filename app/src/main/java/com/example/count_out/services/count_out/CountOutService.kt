package com.example.count_out.services.count_out

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import com.count_out.framework.room.db.traking.WorkoutTable
import com.example.count_out.R
import com.example.count_out.devices.bluetooth.Bluetooth
import com.example.count_out.devices.location.Site
import com.example.count_out.domain.router.Router
import com.example.count_out.entity.Const.NOTIFICATION_EXTRA
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.models.DataForServ
import com.example.count_out.entity.models.DataForUI
import com.example.count_out.framework.notification.NotificationHelper
import com.example.count_out.services.logging.Logging
import com.example.count_out.entity.enums.CommandService
import com.example.count_out.entity.enums.RunningState
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
    private lateinit var workout: WorkoutTable
    var running: Boolean = false
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var messageApp: MessageApp
    @Inject lateinit var ble: Bluetooth
    @Inject lateinit var work: Work
    @Inject lateinit var site: Site
    @Inject lateinit var logging: Logging

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

    fun commandService(command: CommandService){
        when(command){
            CommandService.START_WORK->{ startWork() }
            CommandService.STOP_WORK->{ stopWork() }
            CommandService.PAUSE_WORK->{ pauseWork() }
            CommandService.CONNECT_DEVICE->{ ble.connectDevice(router.dataFromBle, router.dataForBle) }
            CommandService.DISCONNECT_DEVICE->{ ble.disconnectDevice() }
            CommandService.START_SCANNING->{ ble.startScanning(router.dataFromBle) }
            CommandService.STOP_SCANNING->{ ble.stopScanning(router.dataFromBle) }
            CommandService.CLEAR_CACHE_BLE->{ ble.onClearCacheBLE() }
            CommandService.START_LOCATION->{ }
            CommandService.STOP_LOCATION->{ }
            CommandService.SAVE_TRAINING -> { saveTraining()}
            CommandService.NOT_SAVE_TRAINING -> { notSaveTraining()}
        }
    }

    fun startCountOutService(dataForServ: DataForServ): DataForUI {
        running = true
        router = Router(dataForServ)
        startForegroundService()
        sendDataToNotification()
        startSite()
        router.sendData()
        return router.dataForUI
    }

    fun startBle(dataForServ: DataForServ): DataForUI {
        router = Router(dataForServ)
        router.sendBleToUi()
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
        site.start(router.dataFromSite)
        router.dataForSite.state.value = RunningState.Started
    }
    private fun stopSite(){
        if (running) {
            site.stop()
            router.dataForSite.state.value = RunningState.Stopped
            running = false
        }
    }
    private fun startWriteBase(){
        logging.runLogging(router.dataForBase, router.dataFromWork.runningState) }
    private fun stopWriteBase(){ logging.stop()}

    private fun startWork(){
        when(router.dataForUI.runningState.value){
            RunningState.Paused-> router.dataFromWork.runningState.value = RunningState.Started
            RunningState.Stopped, null-> {
                router.dataForWork.training.value?.let { training->
                    workout = WorkoutTable(timeStart = SystemClock.elapsedRealtime(), trainingId = training.idTraining)
                    router.dataFromWork.runningState.value = RunningState.Started
//                    workout.formTraining(training)
                    lg("#################### Start Service Work #################### ")
                    startWriteBase()
                    work.start( router.dataForWork, router.dataFromWork )
                }
            }
            else->{}
        }
    }
    private fun stopWork(){
        lg("#################### Stop Service Work #################### ")
        workout.latitude = router.dataFromSite.coordinate.value?.latitude ?: 0.0
        workout.longitude = router.dataFromSite.coordinate.value?.longitude ?: 0.0
        workout.address = site.getAddressFromLocation(workout.latitude,workout.longitude)
        workout.timeEnd = SystemClock.elapsedRealtime()
        router.dataFromWork.runningState.value = RunningState.Stopped
        stopWriteBase()
    }
    private fun sendDataToNotification(){
        CoroutineScope(Dispatchers.Default).launch {
            router.dataForNotification.collect{
                notificationHelper.updateNotification( data = it,
                    state = router.dataFromWork.runningState.value ?: RunningState.Binding ) }
        }
    }
    private fun pauseWork(){
        lg("#################### Pause Work ####################")
        router.dataFromWork.runningState.value = RunningState.Paused
        notificationHelper.updateNotification(data = router.dataForNotification.value,
            state = router.dataFromWork.runningState.value ?: RunningState.Binding )
//        notificationHelper.setContinueButton()
    }

    private fun saveTraining(){
        lg("saveTraining")
        logging.saveTraining( workout )
    }
    private fun notSaveTraining(){
        logging.notSaveTraining()
    }
}