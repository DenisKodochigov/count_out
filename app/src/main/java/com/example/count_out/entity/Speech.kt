package com.example.count_out.entity

interface Speech {
    var idSpeech: Long
    var beforeStart: String
    var afterStart: String
    var beforeEnd: String
    var afterEnd: String

    var durationBeforeStart: Long
    var durationAfterStart: Long
    var durationBeforeEnd: Long
    var durationAfterEnd: Long
}