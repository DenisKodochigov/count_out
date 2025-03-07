package com.example.count_out.data.repository

import com.example.count_out.domain.repository.trainings.SpeechRepo
import com.example.count_out.data.source.room.SpeechKitSource
import com.example.count_out.entity.models.SpeechKitImpl
import com.example.count_out.entity.workout.speech.SpeechKit
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