package com.count_out.framework.room.source

import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.source.SourceData
import com.count_out.data.source.room.TestSpeechSource
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TestSpeechSourceImpl @Inject constructor(private val dao: SpeechDao): TestSpeechSource, SourceData() {
    override fun get(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>> {
        return executeF { dao.get(speech.idSpeech).map { it?.let { it1 -> it1.toSpeech() } } }
    }
    override fun copy(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>> {
        val result = execute { dao.add(toSpeechTable(speech) )}
        return if (result is ResultDataSource.Success) {
            executeF { dao.get(result.data).map { it?.let { it1 -> it1.toSpeech() } } }
        } else {
            flow { emit (result as ResultDataSource.Error) }
        }
    }

    override fun update(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>> {
        val result: ResultDataSource<Int> = execute { dao.update(toSpeechTable(speech, speech.idSpeech))}
        return if (result is ResultDataSource.Success) {
            executeF { dao.get(speech.idSpeech).map { it?.let { it1 -> it1.toSpeech() } } }
        } else {
            flow { emit (result as ResultDataSource.Error) }
        }
    }
    private fun toSpeechTable(speech: SpeechImplD, idSpeech: Long = 0L) = SpeechTable(
        idSpeech = idSpeech,
        message = speech.message,
        duration = speech.duration,
        addMessage = speech.addMessage,
    )
}