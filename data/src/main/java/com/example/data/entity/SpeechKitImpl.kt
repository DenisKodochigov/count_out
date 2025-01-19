package com.example.data.entity

import com.example.domain.entity.Speech
import com.example.domain.entity.SpeechKit

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
