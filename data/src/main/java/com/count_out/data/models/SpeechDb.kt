package com.count_out.data.models

interface SpeechDb {
    val idSpeech: Long
    val idKit: Long
    val message: String
    val duration: Long
    val addMessage: String
}