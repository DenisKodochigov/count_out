package com.count_out.data.models

import com.count_out.domain.entity.Speech
import com.count_out.domain.entity.SpeechKit

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
