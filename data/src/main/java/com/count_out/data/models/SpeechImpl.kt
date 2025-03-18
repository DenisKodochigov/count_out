package com.count_out.data.models

import com.count_out.domain.entity.workout.Speech

data class SpeechImpl(
    override var idSpeech: Long = 0L,
    override var message: String = "",
    override var duration: Long = 0L,
    override var addMessage: String = "",
): Speech
