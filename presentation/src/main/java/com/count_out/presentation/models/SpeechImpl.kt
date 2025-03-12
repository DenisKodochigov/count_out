package com.count_out.presentation.models

import com.count_out.domain.entity.workout.Speech

data class SpeechImpl(
    override var idSpeech: Long,
    override var message: String,
    override var duration: Long,
    override var addMessage: String,
): Speech
