package com.count_out.data.models

import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit

data class SpeechKitImplD(
    override var idSpeechKit: Long = 0L,
    override val beforeStart: Speech? = null,
    override val afterStart: Speech? = null,
    override val beforeEnd: Speech? = null,
    override val afterEnd: Speech? = null,
): SpeechKit {
    constructor(kit: SpeechKit): this(
        idSpeechKit = kit.idSpeechKit,
        beforeStart = kit.beforeStart,
        afterStart = kit.afterStart,
        beforeEnd = kit.beforeEnd,
        afterEnd = kit.afterEnd,
    )
}
