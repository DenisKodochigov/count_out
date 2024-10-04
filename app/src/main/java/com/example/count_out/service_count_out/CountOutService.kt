package com.example.count_out.service_count_out

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.os.Binder
import android.os.Build
import android.os.IBinder
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
import com.example.count_out.service_count_out.work.WorkN
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class CountOutService @Inject constructor(): Service() {

    val dataForUI: DataForUI = DataForUI()
    private var dataForServ: DataForServ? = null
    val stateService:MutableStateFlow<RunningState> = MutableStateFlow( RunningState.Stopped)
    private val site = Site()
    private lateinit var router: Router
    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var messageApp: MessageApp
    @Inject lateinit var ble: Bluetooth
    @Inject lateinit var work: WorkN

    inner class DistributionServiceBinder: Binder() { fun getService(): CountOutService = this@CountOutService }
    override fun onBind(p0: Intent?): IBinder = DistributionServiceBinder()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.getStringExtra(NOTIFICATION_EXTRA)) {
            RunningState.Started.name -> startWork()
            RunningState.Paused.name -> pauseWork()
            RunningState.Stopped.name -> dataForUI.runningState.value = RunningState.Stopped
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun commandService(command: CommandService, dataServ: DataForServ){
        when(command){
            CommandService.START_SERVICE->{ startCountOutService(dataServ)}
            CommandService.STOP_SERVICE->{ stopCountOutService() }
            CommandService.START_WORK->{ startWork() }
            CommandService.STOP_WORK->{ stopWork() }
            CommandService.PAUSE_WORK->{ pauseWork() }
            CommandService.CONNECT_DEVICE->{
                dataForServ?.let { dataForSrv -> ble.connectDevice( dataForUI, dataForSrv ) } }
            CommandService.DISCONNECT_DEVICE->{ ble.disconnectDevice() }
            CommandService.START_SCANNING->{ ble.startScanning(dataForUI) }
            CommandService.STOP_SCANNING->{ ble.stopScanning(dataForUI) }
            CommandService.CLEAR_CACHE_BLE->{ ble.onClearCacheBLE() }
            CommandService.START_LOCATION->{ }
            CommandService.STOP_LOCATION->{ }
        }
    }

    private fun startCountOutService(dataServ: DataForServ){
        dataForServ = dataServ
        router = Router(dataServ)
        startForegroundService()
        stateService.value = RunningState.Started
//        site.start(router!!.dataForSite, router!!.dataFromSite)
//        startSite()
//        CoroutineScope(Dispatchers.Default).launch {
//            router?.let { it.buffer.coordinate.collect{ coord->
//                lg("Coordinate $coord")
//            } }
//        }
    }
    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31) {
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        }
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
    private fun stopCountOutService(){
        dataForServ = null
        messageApp.messageApi("Stop Distribution Service")
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
    }
    fun getDataForUi(): DataForUI  {
        return router.dataForUI
    }
    fun getDataForUi1() = dataForUI
    private fun startSite(){
        router.dataForSite.state.value = RunningState.Started
    }

    private fun startWork(){
        if (router.dataForUI.runningState.value == RunningState.Paused){
            router.dataFromWork.runningState.value = RunningState.Started
            notificationHelper.setPauseButton()
        }
        if (router.dataForUI.runningState.value == RunningState.Stopped){
            router.dataFromWork.runningState.value = RunningState.Started
            notificationHelper.setPauseButton()
            router.sendDataToUi()
            router.dataFromWork.nextSet.value = router.dataForWork.getSet(0)
            work.start( router.dataFromWork, router.dataForWork )
        }
    }
    private fun stopWork(){
        lg("Stop Work")
        router.dataForUI.runningState.value = RunningState.Stopped
        router.dataForUI.empty()
        router.dataForWork.empty()
    }
    private fun pauseWork(){
        lg("Pause Work")
        router.dataForUI.runningState.value = RunningState.Paused
        notificationHelper.setContinueButton()
    }
    //    private fun startWork(){
//        if (dataForUI.runningState.value == RunningState.Paused){
//            dataForUI.runningState.value = RunningState.Started
//            notificationHelper.setPauseButton()
//        }
//        if (dataForUI.runningState.value == RunningState.Stopped){
//            dataForUI.runningState.value = RunningState.Started
//            notificationHelper.setPauseButton()
//            dataForUI.nextSet.value = dataForServ?.getSet(0)
//            work.start(dataForUI, dataForServ)
//        }
//    }
//    private fun stopWork(){
//        lg("Stop Work")
//        dataForUI.runningState.value = RunningState.Stopped
//        dataForUI.empty()
//        dataForServ?.empty()
//    }
//    private fun pauseWork(){
//        lg("Pause Work")
//        dataForUI.runningState.value = RunningState.Paused
//        notificationHelper.setContinueButton()
//    }

}