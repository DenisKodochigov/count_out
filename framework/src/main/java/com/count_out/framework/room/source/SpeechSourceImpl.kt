package com.count_out.framework.room.source

import com.count_out.data.models.SpeechImpl
import com.count_out.data.source.room.SpeechSource
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechSourceImpl @Inject constructor(private val dao: SpeechDao): SpeechSource {

    override fun get(id: Long): Flow<SpeechImpl> = dao.get(id).map { it.toSpeech() }

    override fun copy(speech: SpeechImpl): Long = dao.add(toSpeechTable(speech)) // idSpeech must be = 0

    override fun update(speech: SpeechImpl) { dao.update(toSpeechTable(speech)) }// idSpeech must be != 0

    override fun del(id: Long) { dao.del(id) }
//    override fun updateDuration(speech: SpeechImpl): Flow<SpeechImpl> {
//        dao.updateDuration(speech.duration, speech.idSpeech)
//        return get(speech.idSpeech)
//    }
    private fun toSpeechTable(speech: SpeechImpl) = SpeechTable(
        idSpeech = 0,
        message = speech.message,
        duration = speech.duration,
        addMessage = speech.addMessage
    )
}