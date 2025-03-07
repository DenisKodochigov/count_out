package com.example.count_out.framework.room.source

import com.example.count_out.data.source.room.SpeechKitSource
import com.example.count_out.data.source.room.SpeechSource
import com.example.count_out.entity.models.SpeechImpl
import com.example.count_out.entity.models.SpeechKitImpl
import com.example.count_out.entity.workout.speech.SpeechKit
import com.example.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.example.count_out.framework.room.db.speech_kit.SpeechKitTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechKitSourceImpl @Inject constructor(
    private val speechSource: SpeechSource,
    private val daoSpeechKit: SpeechKitDao,
) : SpeechKitSource {

    override fun get(id: Long): Flow<SpeechKitImpl> = flow { emit (daoSpeechKit.get(id).toSpeechKit()) }

    override fun add(speechKit: SpeechKit?): Long = daoSpeechKit.add( toSpeechKitTable(speechKit))

    override fun copy(speechKit: SpeechKit?): Long = daoSpeechKit.add( toSpeechKitTable(speechKit))

    override fun update(speechKit: SpeechKit): Flow<SpeechKit> {
        speechSource.update(speechKit.beforeStart as SpeechImpl)
        speechSource.update(speechKit.afterStart as SpeechImpl)
        speechSource.update(speechKit.beforeEnd as SpeechImpl)
        speechSource.update(speechKit.afterEnd as SpeechImpl)
        return flow { emit (daoSpeechKit.get(speechKit.idSpeechKit).toSpeechKit()) }
    }

    override fun del(speechKit: SpeechKit) {
        speechSource.del(speechKit.idBeforeStart)
        speechSource.del(speechKit.idAfterStart)
        speechSource.del(speechKit.idBeforeEnd)
        speechSource.del(speechKit.idAfterEnd)
        daoSpeechKit.del(speechKit.idSpeechKit)
    }

    private fun toSpeechKitTableCopy(speechKit: SpeechKit): SpeechKitTable = SpeechKitTable(
        idBeforeStart = speechKit.idBeforeStart,
        idAfterStart = speechKit.idAfterStart,
        idBeforeEnd = speechKit.idBeforeEnd,
        idAfterEnd = speechKit.idBeforeEnd,
    )

    private fun toSpeechKitTable(speechKit: SpeechKit?): SpeechKitTable {
        return speechKit?.let {
            SpeechKitTable(
                idBeforeStart = speechSource.add(it.beforeStart),
                idAfterStart = speechSource.add(it.afterStart),
                idBeforeEnd = speechSource.add(it.beforeEnd),
                idAfterEnd = speechSource.add(it.afterEnd),)
        } ?: SpeechKitTable(
            idBeforeStart = speechSource.add(null),
            idAfterStart = speechSource.add(null),
            idBeforeEnd = speechSource.add(null),
            idAfterEnd = speechSource.add(null),)

    }
}