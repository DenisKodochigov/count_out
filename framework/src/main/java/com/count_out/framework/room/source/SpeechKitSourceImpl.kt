package com.count_out.framework.room.source

import com.count_out.data.models.SpeechImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.data.source.room.SpeechSource
import com.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.count_out.framework.room.db.speech_kit.SpeechKitTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechKitSourceImpl @Inject constructor(
    private val speechSource: SpeechSource,
    private val daoSpeechKit: SpeechKitDao,
) : SpeechKitSource {

    override fun get(id: Long): Flow<SpeechKitImpl> = daoSpeechKit.get(id).map { it.toSpeechKit() }

    override fun add(): Flow<SpeechKitImpl> {
        return get( daoSpeechKit.add( toSpeechKitTableNew()))
    }
    override fun addL(): Long = daoSpeechKit.add( toSpeechKitTableNew())

    override fun copy(speechKit: SpeechKitImpl): Flow<SpeechKitImpl> {
        val speechKitTable =
            if (speechKit.idSpeechKit == 0L) { toSpeechKitTableNew() }
            else {toSpeechKitTableCopy(speechKit)}
        return get(daoSpeechKit.add( speechKitTable))
    }
    override fun copyL(speechKit: SpeechKitImpl): Long {
        val speechKitTable =
            if (speechKit.idSpeechKit == 0L) { toSpeechKitTableNew() }
            else {toSpeechKitTableCopy(speechKit)}
        return daoSpeechKit.add( speechKitTable)
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
        idBeforeStart = speechKit.idBeforeStart,
        idAfterStart = speechKit.idAfterStart,
        idBeforeEnd = speechKit.idBeforeEnd,
        idAfterEnd = speechKit.idBeforeEnd,
    )

    private fun toSpeechKitTableNew(): SpeechKitTable {
        return SpeechKitTable(idBeforeStart = 0, idAfterStart = 0, idBeforeEnd = 0, idAfterEnd = 0,)
    }
}