package com.count_out.data.models.entity

import com.count_out.data.models.Data
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Speech

abstract class SpeechDb: Data {
    abstract val idSpeech: Long
    abstract val setId: Long?
    abstract val exerciseId: Long?
    abstract val ringId: Long?
    abstract val partId: Long?
    abstract val planId: Long?
    abstract val message: String
    abstract val duration: Long
    abstract val addMessage: String
//    override fun toResultData(): ResultData<Data> = ResultData.Success(this)
    override fun toDomain(ind: Int) = object: Speech {
        override val idSpeech: Long = this@SpeechDb.idSpeech
        override val message: String = this@SpeechDb.message
        override val duration: Long = this@SpeechDb.duration
        override val addMessage: String = this@SpeechDb.addMessage
        override val setId: Long? = this@SpeechDb.setId
        override val exerciseId: Long? = this@SpeechDb.exerciseId
        override val ringId: Long? = this@SpeechDb.ringId
        override val partId: Long? = this@SpeechDb.partId
        override val planId: Long? = this@SpeechDb.planId
    }
    companion object {
        fun fromDomain(domain: Domain): SpeechDb {
            return when (domain) {
                is Speech -> object: SpeechDb(){
                    override val idSpeech: Long = (domain).idSpeech
                    override val setId: Long? = (domain).setId
                    override val exerciseId: Long? = (domain).exerciseId
                    override val ringId: Long? = (domain).ringId
                    override val partId: Long? = (domain).partId
                    override val planId: Long? = (domain).planId
                    override val message: String = (domain).message
                    override val duration: Long = (domain).duration
                    override val addMessage: String = (domain).addMessage
                }
                else -> throw IllegalArgumentException("Unsupported domain type")
            }
        }
    }
}