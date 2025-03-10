package com.count_out.presentation.modeles

import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.entity.workout.SpeechKit

data class SpeechKitImpl(
    override var idSpeechKit: Long,
    override val idBeforeStart: Long,
    override val idAfterStart: Long,
    override val idBeforeEnd: Long,
    override val idAfterEnd: Long,
    override val beforeStart: Speech?,
    override val afterStart: Speech?,
    override val beforeEnd: Speech?,
    override val afterEnd: Speech?,
): SpeechKit
