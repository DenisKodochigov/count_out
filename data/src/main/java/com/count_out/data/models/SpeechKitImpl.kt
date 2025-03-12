package com.count_out.data.models

import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit

data class SpeechKitImpl(
    override var idSpeechKit: Long = 0L,
    override val idBeforeStart: Long = 0L,
    override val idAfterStart: Long = 0L,
    override val idBeforeEnd: Long = 0L,
    override val idAfterEnd: Long = 0L,
    override val beforeStart: Speech? = null,
    override val afterStart: Speech? = null,
    override val beforeEnd: Speech? = null,
    override val afterEnd: Speech? = null,
): SpeechKit
