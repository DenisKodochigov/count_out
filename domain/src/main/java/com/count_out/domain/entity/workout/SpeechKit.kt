package com.count_out.domain.entity.workout

interface SpeechKit {
    var idSpeechKit: Long
    val beforeStart: Speech?
    val afterStart: Speech?
    val beforeEnd: Speech?
    val afterEnd: Speech?
}
