package com.count_out.data.entity

import com.count_out.domain.entity.Speech

data class SpeechImpl(
    override var idSpeech: Long,
    override var message: String,
    override var duration: Long,
    override var addMessage: String,
): Speech
