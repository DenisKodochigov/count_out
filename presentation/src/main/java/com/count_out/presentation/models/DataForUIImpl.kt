package com.count_out.presentation.models

import android.util.Pair
import com.count_out.domain.entity.Coordinate
import com.count_out.domain.entity.StepTraining
import com.count_out.domain.entity.TickTime
import com.count_out.domain.entity.router.Buffer
import com.count_out.domain.entity.router.DataForUI
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.enums.RunningState
import java.util.Collections.emptyList
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForUIImpl (
    override val runningState: MutableStateFlow<RunningState?> = MutableStateFlow(null),
    override val flowTime: MutableStateFlow<TickTime?> = MutableStateFlow(null),
    override val countRest: MutableStateFlow<Int> = MutableStateFlow(0),
    override val currentCount: MutableStateFlow<Int> = MutableStateFlow(0),
    override val currentDuration: MutableStateFlow<Int> = MutableStateFlow(0),
    override val currentDistance: MutableStateFlow<Int> = MutableStateFlow(0),
    override val enableChangeInterval: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val stepTraining: MutableStateFlow<StepTraining?> = MutableStateFlow(null),
    override val durationSpeech: MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),

    override val heartRate: MutableStateFlow<Int> = MutableStateFlow(0),
    override val scannedBle: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val bleConnectState: MutableStateFlow<ConnectState> = MutableStateFlow(ConnectState.NOT_CONNECTED),
    override val foundDevices: MutableStateFlow<List<DeviceUI>> = MutableStateFlow(emptyList()),
    override val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow( null),
    override var cancelCoroutineWork: ()-> Unit = {}
): DataForUI {
    override fun setWork(buffer: Buffer){
        this.flowTime.value = buffer.flowTime.value
        this.countRest.value = buffer.countRest.value
        this.enableChangeInterval.value = buffer.enableChangeInterval.value
//        this.durationSpeech.value = buffer.durationSpeech.value
    }
    override fun setBle(buffer: Buffer){
        this.heartRate.value = buffer.heartRate.value
        this.scannedBle.value = buffer.scannedBle.value
        this.bleConnectState.value = buffer.bleConnectState.value
        this.foundDevices.value = buffer.foundDevices.value
        this.coordinate.value = buffer.coordinate.value
    }
}
