package com.example.data.repository

import com.example.data.entity.SpeechKitImpl
import com.example.data.source.room.SpeechKitSource
import com.example.domain.entity.SpeechKit
import com.example.domain.repository.trainings.SpeechRepo
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