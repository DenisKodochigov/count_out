package com.example.count_out.service_count_out.location

import com.example.count_out.data.room.tables.CoordinateDB
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.router.DataForSite
import com.example.count_out.entity.router.DataFromSite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Site {

    fun start(dataForSite: DataForSite, dataFromSite: DataFromSite){
        CoroutineScope(Dispatchers.Default).launch {
            var count = 0L
            while (true){
                dataFromSite.coordinate.value = CoordinateDB(0.0,0.0,count)
//                lg("dataForSite $dataForSite; dataFromSite:$dataFromSite")
                count++
                delay(1000L)
            }
        }
    }
    fun stop(dataForSite: DataForSite){dataForSite.state.value = RunningState.Stopped}
    fun pause(dataForSite: DataForSite){dataForSite.state.value = RunningState.Paused}
}
