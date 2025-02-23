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

    override fun add(speech: SpeechImpl): Flow<SpeechImpl> {
        dao.add(toSpeechTable(speech))
        return get(dao.add(toSpeechTable(speech)))
    }

    override fun update(speech: SpeechImpl): Flow<SpeechImpl> {
        dao.update(toSpeechTable(speech).copy(idSpeech = speech.idSpeech))
        return get(speech.idSpeech)
    }

    override fun updateDuration(speech: SpeechImpl): Flow<SpeechImpl> {
        dao.updateDuration(speech.duration, speech.idSpeech)
        return get(speech.idSpeech)
    }

    override fun del(id: Long) { dao.del(id) }

    private fun toSpeechTable(speech: SpeechImpl) = SpeechTable(
        message = speech.message,
        duration = speech.duration,
        addMessage = speech.addMessage
    )
}