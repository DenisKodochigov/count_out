package com.count_out.framework.room.source

import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.source.SourceData
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.count_out.framework.room.db.speech_kit.SpeechKitTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechKitSourceImpl @Inject constructor(
    private val speechSource: SpeechSourceImpl,
    private val dao: SpeechKitDao,
) : SpeechKitSource, SourceData() {

    override fun get(speechKit: SpeechKitImplD): Flow<ResultDataSource<SpeechKitImplD>> =
        getResultFlow{ dao.get(speechKit.idSpeechKit).map { it?.toSpeechKit() }}

    override fun copy(speechKit: SpeechKitImplD): Flow<ResultDataSource<SpeechKitImplD>> {
        return copyValue(speechKit)?.let {
            getResultFlow { dao.get(speechKit.idSpeechKit).map { it?.toSpeechKit() } }
        } ?: flow { emit (resultNullException()) }
    }

    override fun update(speechKit: SpeechKitImplD): Flow<ResultDataSource<SpeechKitImplD>> {
        speechKit.beforeStart?.let { speechSource.updateValue( it as SpeechImplD) }
        speechKit.afterStart?.let { speechSource.updateValue( it as SpeechImplD) }
        speechKit.beforeEnd?.let { speechSource.updateValue( it as SpeechImplD) }
        speechKit.afterEnd?.let { speechSource.updateValue( it as SpeechImplD) }
        return flow { emit (getResult{ speechKit })}
    }

    override fun del(speechKit: SpeechKitImplD): Flow<ResultDataSource<Long>> {
        return flow { emit (
            getResult{
                speechKit.beforeStart?.let { speechSource.delValue( it.idSpeech )?.let{
                    speechKit.afterStart?.let { speechSource.delValue( it.idSpeech )?.let{
                        speechKit.beforeEnd?.let { speechSource.delValue( it.idSpeech )?.let{
                            speechKit.afterEnd?.let { speechSource.delValue( it.idSpeech )?.let{
                                dao.del(speechKit.idSpeechKit)?.toLong()
                            }}
                        }}
                    }}
                }}
            }
        )}
    }

    fun copyValue(speechKit: SpeechKitImplD): Long? {
        return dao.add(
            SpeechKitTable(
                idBeforeStart = copySpeech(speechKit.beforeStart as SpeechImplD),
                idAfterStart = copySpeech(speechKit.beforeEnd as SpeechImplD),
                idBeforeEnd = copySpeech(speechKit.afterStart as SpeechImplD),
                idAfterEnd = copySpeech(speechKit.afterEnd as SpeechImplD),
        ))
    }
    fun copySpeech(speech: SpeechImplD?): Long {
        val result = speech?.let {
            if (it.idSpeech > 0) speechSource.copyValue(it)
            else speechSource.copyValue(SpeechImplD())
        } ?: speechSource.copyValue(SpeechImplD())
        return result ?: 0L
    }
}
