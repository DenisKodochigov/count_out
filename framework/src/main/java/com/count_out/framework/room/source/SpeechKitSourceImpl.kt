package com.count_out.framework.room.source

import com.count_out.data.models.SpeechImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.data.source.room.SpeechSource
import com.count_out.domain.entity.workout.SpeechKit
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

    override fun copy(speechKit: SpeechKit): Long = daoSpeechKit.add( toSpeechKitTable(speechKit) )

    override fun update(speechKit: SpeechKit) {
        speechSource.update(speechKit.beforeStart as SpeechImpl)
        speechSource.update(speechKit.afterStart as SpeechImpl)
        speechSource.update(speechKit.beforeEnd as SpeechImpl)
        speechSource.update(speechKit.afterEnd as SpeechImpl)
    }

    override fun del(speechKit: SpeechKit) {
        speechSource.del(speechKit.idBeforeStart)
        speechSource.del(speechKit.idAfterStart)
        speechSource.del(speechKit.idBeforeEnd)
        speechSource.del(speechKit.idAfterEnd)
        daoSpeechKit.del(speechKit.idSpeechKit)
    }

    private fun toSpeechKitTable(speechKit: SpeechKit): SpeechKitTable = SpeechKitTable(
        idSpeechKit = 0,
        idBeforeStart = speechKit.idBeforeStart,
        idAfterStart = speechKit.idAfterStart,
        idBeforeEnd = speechKit.idBeforeEnd,
        idAfterEnd = speechKit.idBeforeEnd,
    )
}