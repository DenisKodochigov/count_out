package com.count_out.data.repository

import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.repository.trainings.SpeechRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeechRepoImpl @Inject constructor(private val speechKitSource: SpeechKitSource): SpeechRepo {
//    override fun get(id: Long): Flow<SpeechKit> = speechKitSource.get(id)
//
//    override fun del(speechKit: SpeechKit) { speechKitSource.del(speechKit) }
//
//    override fun add(speechKit: SpeechKit?): Flow<SpeechKit> = speechKitSource.add(speechKit)

    override fun update(speechKit: SpeechKit): Flow<SpeechKit> =
        speechKitSource.update(speechKit as SpeechKitImpl)
}