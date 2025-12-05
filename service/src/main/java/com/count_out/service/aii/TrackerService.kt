package com.count_out.service.aii

import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.count_out.data.source.services.TelemetrySource
import com.count_out.device.bluetooth.ai.BleHeartRateManager
import com.count_out.device.bluetooth.ai.HeartRateProvider
import com.count_out.device.location.ai.LocationProvider
import com.count_out.domain.entity.ai.TelemetryPoint
import com.count_out.domain.entity.ai.TrainingState
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TrackerService: LifecycleService() {
    @Inject lateinit var locationProvider: LocationProvider
    @Inject lateinit var heartRateProvider: HeartRateProvider
    @Inject lateinit var source: TelemetrySource
    private val state = MutableStateFlow(TrainingState.STOPPED)
    private val buffer = mutableListOf<TelemetryPoint>()
    private val batchSize = 20
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var lastHeartRate: Int? = null
    val notification = NotificationCompat.Builder(this, TRACKER_CHANNEL_ID)
        .setContentTitle("Training running")
        .setContentText("Tracking your workout...")
//        .setSmallIcon(R.drawable.ic_run)
        .setOngoing(true)
        .build()
    override fun onCreate() {
        super.onCreate()
        startForeground(1, notification)
        observeSensors()
        lifeScope{ heartRateProvider.heartRateFlow.collect { rrList -> /*_______use hr_______*/ } }
        lifeScope{ heartRateProvider.startScanAndConnect(10_000L) }
        lifeScope{ heartRateProvider.rrIntervalFlow.collect { rrList -> /*___________________*/ } }
        lifeScope{ heartRateProvider.connectionStateFlow.collect { state -> /*update notification/UI*/ } }
    }
    private fun observeSensors() {
        serviceScope.launch {
            locationProvider.locationFlow.collect { loc ->
                if (state.value == TrainingState.RUNNING) {
                    addPoint(TelemetryPoint(loc.time, loc.lat, loc.lon, lastHeartRate)) }
            }
        }
        serviceScope.launch { heartRateProvider.heartRateFlow.collect { hr -> lastHeartRate = hr } }
    }
    private fun addPoint(point: TelemetryPoint) {
        buffer.add(point)
        if (buffer.size >= batchSize) {
            source.insertBatch(buffer.toList())
            buffer.clear()
        }
    }

    fun onBind(intent: Intent?): IBinder? = intent?.let{super.onBind(intent)}

    override fun onDestroy() {
        serviceScope.cancel()
        (heartRateProvider as? BleHeartRateManager)?.shutdown()
        super.onDestroy()
    }
    fun lifeScope(run: suspend ()->Unit){
        lifecycleScope.launch { repeatOnLifecycle(Lifecycle.State.CREATED) { run() } }
    }
}
//@AndroidEntryPoint
//class TrackerService1 : Service() {
//    @Inject lateinit var telemetryBatchWriterChannel: TelemetryBatchWriterChannel
//    @Inject lateinit var workManager: WorkManager
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return super.onBind(intent)
//    }
//    override fun onCreate() {
//        super.onCreate()
//        // запланировать periodic upload один раз
//        val request = PeriodicWorkRequestBuilder<TelemetryUploadWorker>(15, TimeUnit.MINUTES)
//            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.SECONDS)
//            .build()
//        workManager.enqueueUniquePeriodicWork(
//            "telemetry_upload", ExistingPeriodicWorkPolicy.KEEP, request)
//    }
//    private fun onNewSample(location: Location?, hr: Int?) {
//        val entry = TelemetryEntity(
//            timestamp = System.currentTimeMillis(),
//            lat = location?.latitude ?: 0.0,
//            lon = location?.longitude ?: 0.0,
//            heartRate = hr,
//            synced = false
//        )
//        runBlocking { telemetryBatchWriterChannel.add(entry) }
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        runBlocking { telemetryBatchWriterChannel.closeAndFlush() }
//    }
//}