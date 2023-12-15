package com.example.count_out.entity

interface SpeechActivity {
    val idSpeech: Long
    val soundBeforeStart: String
    val soundAfterStart: String
    val soundBeforeEnd: String
    val soundAfterEnd: String
}