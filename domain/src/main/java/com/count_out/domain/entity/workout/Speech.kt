package com.count_out.domain.entity.workout

interface Speech: Domain {
    val idSpeech: Long
    val message: String
    val duration: Long
    val addMessage: String

    val setId: Long?
    val exerciseId: Long?
    val ringId: Long?
    val partId: Long?
    val planId: Long?
    companion object{
        val EMPTY  = object: Speech{
            override val idSpeech: Long = 0
            override val message: String = ""
            override val duration: Long = 0
            override val addMessage: String = ""
            override val setId: Long? = null
            override val exerciseId: Long? = null
            override val ringId: Long? = null
            override val partId: Long? = null
            override val planId: Long? = null
        }
    }
}