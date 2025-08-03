package com.count_out.data.models

import com.count_out.domain.entity.workout.Speech

data class SpeechImplD(
    override var idSpeech: Long = 0L,
    override var message: String = "",
    override var duration: Long = 0L,
    override var addMessage: String = "",
): Speech{
    constructor(item: Speech?) : this(
        idSpeech = item?.idSpeech ?: 0L,
        message = item?.message ?: "",
        duration = item?.duration ?: 0L,
        addMessage = item?.addMessage ?: ""
    )
}
