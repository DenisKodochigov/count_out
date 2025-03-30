package com.count_out.framework.room.source

import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
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

    override fun get(id: Long): Flow<SpeechKitImplD?> =
        daoSpeechKit.get(id).map { it?.let { it1-> it1.toSpeechKit() } ?: null }

    override fun copy(speechKit: SpeechKitImplD): Long {
        return  daoSpeechKit.add( toSpeechKitTable(speechKit) )
    }

    override fun update(speechKit: SpeechKitImplD) {
        speechSource.update(speechKit.beforeStart as SpeechImplD)
        speechSource.update(speechKit.afterStart as SpeechImplD)
        speechSource.update(speechKit.beforeEnd as SpeechImplD)
        speechSource.update(speechKit.afterEnd as SpeechImplD)
    }

    override fun del(speechKit: SpeechKitImplD) {
        speechSource.del(speechKit.idBeforeStart)
        speechSource.del(speechKit.idAfterStart)
        speechSource.del(speechKit.idBeforeEnd)
        speechSource.del(speechKit.idAfterEnd)
        daoSpeechKit.del(speechKit.idSpeechKit)
    }

    private fun toSpeechKitTable(speechKit: SpeechKitImplD): SpeechKitTable {
        return SpeechKitTable(
            idSpeechKit = 0,
            idBeforeStart = speechSource.copy(speechKit.beforeStart?.let { it as SpeechImplD} ?: SpeechImplD()),
            idAfterStart = speechSource.copy(speechKit.afterStart?.let { it as SpeechImplD} ?: SpeechImplD()),
            idBeforeEnd = speechSource.copy(speechKit.beforeEnd?.let { it as SpeechImplD} ?: SpeechImplD()),
            idAfterEnd = speechSource.copy(speechKit.afterEnd?.let { it as SpeechImplD} ?: SpeechImplD()),
        )
    }
}