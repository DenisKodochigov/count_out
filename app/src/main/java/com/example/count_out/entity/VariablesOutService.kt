package com.example.count_out.entity

import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.no_use.MessageWorkOut
import com.example.count_out.service.stopwatch.StopWatchNew
import kotlinx.coroutines.flow.MutableStateFlow

data class VariablesOutService (
    var flowTick: MutableStateFlow<TickTime> = MutableStateFlow(TickTime()),
    val messageList: MutableStateFlow<List<MessageWorkOut>> = MutableStateFlow(emptyList()),
    val set: MutableStateFlow<Set> = MutableStateFlow(SetDB() as Set),
    val durationSpeech:MutableStateFlow<Pair<Long, Long>> = MutableStateFlow(Pair(0,0)),
    val stateRunning: MutableStateFlow<StateRunning> = MutableStateFlow(StateRunning.Created),
){
    fun addMessage(text: String){
        val list: MutableList<MessageWorkOut> = messageList.value.toMutableList()
        list.add( MessageWorkOut(message = text, tickTime = StopWatchNew.getTickTime().value))
        messageList.value = list
    }
    fun cancel(){
        messageList.value = emptyList()
        stateRunning.value = StateRunning.Stopped
        set.value = SetDB()
        durationSpeech.value = Pair(0,0)
    }
}
