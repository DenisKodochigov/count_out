package com.count_out.data.models

import com.count_out.domain.entity.workout.SpeechKit

data class SpeechKitImplD(
    override var idSpeechKit: Long = 0L,
    override val beforeStart: SpeechImplD? = null,
    override val afterStart: SpeechImplD? = null,
    override val beforeEnd: SpeechImplD? = null,
    override val afterEnd: SpeechImplD? = null,
): SpeechKit {
    constructor(kit: SpeechKit): this(
        idSpeechKit = kit.idSpeechKit,
        beforeStart = SpeechImplD(kit.beforeStart),
        afterStart = SpeechImplD(kit.afterStart),
        beforeEnd = SpeechImplD(kit.beforeEnd),
        afterEnd = SpeechImplD(kit.afterEnd),
    )
}
