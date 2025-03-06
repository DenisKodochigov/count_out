package com.example.count_out.entity.workout.speech

interface SpeechKit {
    var idSpeechKit: Long
    val idBeforeStart: Long
    val idAfterStart: Long
    val idBeforeEnd: Long
    val idAfterEnd: Long
    val beforeStart: Speech
    val afterStart: Speech
    val beforeEnd: Speech
    val afterEnd: Speech
}
