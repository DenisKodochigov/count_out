package com.count_out.presentation.models

import com.count_out.domain.entity.workout.Speech

data class SpeechImplP(
    override var idSpeech: Long = 0L,
    override var message: String = "",
    override var duration: Long = 0L,
    override var addMessage: String = "",
): Speech{
    constructor( item: Speech) : this(
        idSpeech = item.idSpeech,
        message = item.message,
        duration = item.duration,
        addMessage = item.addMessage
    )
}
