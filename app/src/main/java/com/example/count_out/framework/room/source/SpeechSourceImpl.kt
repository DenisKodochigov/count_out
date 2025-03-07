package com.example.count_out.framework.room.source

import com.example.count_out.data.source.room.SpeechSource
import com.example.count_out.entity.workout.speech.Speech
import com.example.count_out.framework.room.db.speech.SpeechDao
import com.example.count_out.framework.room.db.speech.SpeechTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechSourceImpl @Inject constructor(private val dao: SpeechDao): SpeechSource {

    override fun get(id: Long): Flow<Speech> = flow { emit (dao.get(id).toSpeech())}

//    override fun add(speech: Speech): Flow<Speech> {
//        return get(dao.add(toSpeechTable(speech)))
//    }

    override fun add( speech: Speech?): Long {
        val id = dao.add(toSpeechTable(speech))
        return id
    }
    override fun copy( speech: Speech?): Long {
        val id = dao.add(toSpeechTable(speech))
        return id
    }
    override fun update(speech: Speech): Flow<Speech> {
        dao.update(toSpeechTable(speech).copy(idSpeech = speech.idSpeech))
        return get(speech.idSpeech)
    }

    override fun updateDuration(speech: Speech): Flow<Speech> {
        dao.updateDuration(speech.duration, speech.idSpeech)
        return get(speech.idSpeech)
    }

    override fun del(id: Long) { dao.del(id) }

    private fun toSpeechTable(speech: Speech?): SpeechTable{
        return speech?.let {
            SpeechTable(
                message = it.message,
                duration = it.duration,
                addMessage = it.addMessage
            )
        } ?: SpeechTable()
    }
}