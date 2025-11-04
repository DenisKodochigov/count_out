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
    fun copy(
        idSpeech: Long = this.idSpeech,
        message: String = this.message,
        duration: Long = this.duration,
        addMessage: String = this.addMessage,
        setId: Long? = this.setId,
        exerciseId: Long? = this.exerciseId,
        ringId: Long? = this.ringId,
        partId: Long? = this.partId,
        planId: Long? = this.planId
    ) = object: Speech {
        override val idSpeech: Long = idSpeech
        override val message: String = message
        override val duration: Long = duration
        override val addMessage: String = addMessage
        override val setId: Long? = setId
        override val exerciseId: Long? = exerciseId
        override val ringId: Long? = ringId
        override val partId: Long? = partId
        override val planId: Long? = planId
    }
    companion object{
        val EMPTY = object: Speech {
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