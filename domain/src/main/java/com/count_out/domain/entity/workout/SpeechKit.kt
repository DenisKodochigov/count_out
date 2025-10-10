package com.count_out.domain.entity.workout

interface SpeechKit: Domain {
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
        fun fill(list: List<Speech>) = object: SpeechKit{
            override val beforeStart: Speech = list[0]
            override val afterStart: Speech = list[1]
            override val beforeEnd: Speech = list[2]
            override val afterEnd: Speech = list[3]
        }
    }
}
