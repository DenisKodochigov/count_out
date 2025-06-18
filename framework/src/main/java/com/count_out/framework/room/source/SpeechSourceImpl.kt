package com.count_out.framework.room.source

import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.source.SourceData
import com.count_out.data.source.room.SpeechSource
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechSourceImpl @Inject constructor(private val dao: SpeechDao): SpeechSource, SourceData() {

    override fun get(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>> {
        return getResultFlow { dao.getFlow(speech.idSpeech).map { it?.toSpeech() } }
    }

    override fun copy(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>> {
        val result = getResult { copyValue(speech) }
        return if (result is ResultDataSource.Success){
                 getResultFlow { dao.getFlow(result.data).map { it?.toSpeech() } }
                } else flow { emit (result as ResultDataSource.Error) }
    }
    override fun del(speech: SpeechImplD): Flow<ResultDataSource<Int>> =
         flow { emit (getResult{delValue(speech.idSpeech) as Int})}

    override fun update(speech: SpeechImplD): Flow<ResultDataSource<SpeechImplD>> {
        return updateValue(speech)?.let {
                getResultFlow { dao.getFlow(speech.idSpeech).map { it?.toSpeech() } }
                } ?: flow { emit (resultNullException()) }
    }
    private fun toSpeechTable(speech: SpeechImplD, idSpeech: Long = 0L) = SpeechTable(
        idSpeech = idSpeech,
        message = speech.message,
        duration = speech.duration,
        addMessage = speech.addMessage,
    )
    fun getValue(id: Long) = dao.get(id)
    fun delValue(id: Long) = dao.del(id)
    fun copyValue(speech: SpeechImplD) = dao.add(toSpeechTable(speech))
    fun copyById(id: Long): Long {
        return if ( id == 0L){ dao.add(SpeechTable()) ?: 0L }
        else { dao.get(id)?.let { dao.add(it) ?: 0L } ?: dao.add(SpeechTable()) ?: 0 }
    }
    fun updateValue(speech: SpeechImplD) = dao.update(toSpeechTable(speech, speech.idSpeech))
}