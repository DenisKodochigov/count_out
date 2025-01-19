package com.example.data.entity

import com.example.domain.entity.Speech

data class SpeechImpl(
    override var idSpeech: Long,
    override var message: String,
    override var duration: Long,
    override var addMessage: String,
): Speech
