package com.count_out.domain.entity.workout

interface SpeechKit: Element {
    val idSpeechKit: Long
    val beforeStart: Speech
    val afterStart: Speech
    val beforeEnd: Speech
    val afterEnd: Speech
}
