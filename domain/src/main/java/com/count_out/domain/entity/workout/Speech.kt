package com.count_out.domain.entity.workout

interface Speech {
    val idSpeech: Long
    val idKit: Long
    val message: String
    val duration: Long
    val addMessage: String
}