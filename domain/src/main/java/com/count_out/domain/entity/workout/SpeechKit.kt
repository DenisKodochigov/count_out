package com.count_out.domain.entity.workout

interface SpeechKit: Element {
    val beforeStart: Speech
    val afterStart: Speech
    val beforeEnd: Speech
    val afterEnd: Speech

    companion object{
        val EMPTY = object: SpeechKit{
            override val beforeStart: Speech = Speech.EMPTY
            override val afterStart: Speech = Speech.EMPTY
            override val beforeEnd: Speech = Speech.EMPTY
            override val afterEnd: Speech = Speech.EMPTY
        }
    }
}
