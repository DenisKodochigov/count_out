package com.example.count_out.entity.models

import com.example.count_out.entity.workout.speech.Speech
import com.example.count_out.entity.workout.speech.SpeechKit

data class SpeechKitImpl(
    override var idSpeechKit: Long = 0,
    override val idBeforeStart: Long = 0L,
    override val idAfterStart: Long = 0L,
    override val idBeforeEnd: Long = 0L,
    override val idAfterEnd: Long = 0L,
    override val beforeStart: Speech? = null,
    override val afterStart: Speech? = null,
    override val beforeEnd: Speech? = null,
    override val afterEnd: Speech? = null,
): SpeechKit