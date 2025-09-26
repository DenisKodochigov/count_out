package com.count_out.domain.entity.workout

interface Speech {
    val idSpeech: Long
    val idKit: Long
    val message: String
    val duration: Long
    val addMessage: String
    companion object{
        val EMPTY  = object: Speech{
            override val idSpeech: Long = 0
            override val idKit: Long = 0
            override val message: String = ""
            override val duration: Long = 0
            override val addMessage: String = ""
        }
    }
}