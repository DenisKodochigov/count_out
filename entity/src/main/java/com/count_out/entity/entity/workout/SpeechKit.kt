package com.count_out.entity.entity.workout

interface SpeechKit {
    var idSpeechKit: Long
    val idBeforeStart: Long
    val idAfterStart: Long
    val idBeforeEnd: Long
    val idAfterEnd: Long
    val beforeStart: Speech?
    val afterStart: Speech?
    val beforeEnd: Speech?
    val afterEnd: Speech?
}
