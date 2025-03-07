package com.example.count_out.entity.models

import com.example.count_out.entity.workout.speech.Speech

data class SpeechImpl(
    override var idSpeech: Long = 0,
    override var message: String = "",
    override var duration: Long = 0L,
    override var addMessage: String = "",
): Speech
