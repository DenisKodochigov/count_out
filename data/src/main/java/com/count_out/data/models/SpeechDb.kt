package com.count_out.data.models

interface SpeechDb: Data {
    val idSpeech: Long
    val setId: Long?
    val exerciseId: Long?
    val ringId: Long?
    val partId: Long?
    val planId: Long?
    val message: String
    val duration: Long
    val addMessage: String
}