package com.example.framework.room.source

import com.example.data.entity.SpeechImpl
import com.example.data.entity.SpeechKitImpl
import com.example.data.source.room.SpeechKitSource
import com.example.framework.room.db.speech_kit.SpeechKitDao
import com.example.framework.room.db.speech_kit.SpeechKitTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechKitSourceImpl @Inject constructor(
    private val speechSource: SpeechSourceImpl,
    private val daoSpeechKit: SpeechKitDao,
) : SpeechKitSource {

    override fun get(id: Long): Flow<SpeechKitImpl> = daoSpeechKit.get(id).map { it.toSpeechKit() }

    override fun add(speechKit: SpeechKitImpl): Flow<SpeechKitImpl> {
        val speechKitTable =
            if (speechKit.idSpeechKit == 0L) { toSpeechKitTableNew() } else {toSpeechKitTableCopy(speechKit)}
        daoSpeechKit.add(speechKitTable)
        return get(speechKit.idSpeechKit)
    }

    override fun update(speechKit: SpeechKitImpl): Flow<SpeechKitImpl> {
        speechSource.update(speechKit.beforeStart as SpeechImpl)
        speechSource.update(speechKit.afterStart as SpeechImpl)
        speechSource.update(speechKit.beforeEnd as SpeechImpl)
        speechSource.update(speechKit.afterEnd as SpeechImpl)
        return daoSpeechKit.get(speechKit.idSpeechKit).map { it.toSpeechKit() }
    }

    override fun del(speechKit: SpeechKitImpl) {
        speechSource.del(speechKit.idBeforeStart)
        speechSource.del(speechKit.idAfterStart)
        speechSource.del(speechKit.idBeforeEnd)
        speechSource.del(speechKit.idAfterEnd)
        daoSpeechKit.del(speechKit.idSpeechKit)
    }

    private fun toSpeechKitTableCopy(speechKit: SpeechKitImpl): SpeechKitTable = SpeechKitTable(
        idBeforeStart = (speechSource.add((
                speechKit.beforeStart as SpeechImpl).copy(idSpeech = 0L))as StateFlow).value.idSpeech,
        idAfterStart = (speechSource.add((
                speechKit.afterStart as SpeechImpl).copy(idSpeech = 0L)) as StateFlow).value.idSpeech,
        idBeforeEnd = (speechSource.add((
                speechKit.beforeEnd as SpeechImpl).copy(idSpeech = 0L)) as StateFlow).value.idSpeech,
        idAfterEnd = (speechSource.add((
                speechKit.afterEnd as SpeechImpl).copy(idSpeech = 0L)) as StateFlow).value.idSpeech,
    )

    private fun toSpeechKitTableNew(): SpeechKitTable {
        val speech = SpeechImpl(0, "", 0, "")
        return SpeechKitTable(
            idBeforeStart = (speechSource.add(speech) as StateFlow).value.idSpeech,
            idAfterStart = (speechSource.add(speech) as StateFlow).value.idSpeech,
            idBeforeEnd = (speechSource.add(speech) as StateFlow).value.idSpeech,
            idAfterEnd = (speechSource.add(speech) as StateFlow).value.idSpeech,
        )
    }
}