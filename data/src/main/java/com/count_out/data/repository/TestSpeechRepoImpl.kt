package com.count_out.data.repository

import com.count_out.data.source.room.TestSpeechSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.domain.repository.trainings.TestSpeechRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestSpeechRepoImpl@Inject constructor(
    private val speechSource: TestSpeechSource): TestSpeechRepo  {
    override fun get(id: Long): Flow<ResultUC<SpeechKit>> {
        TODO("Not yet implemented")
    }

    override fun del(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>> {
        TODO("Not yet implemented")
    }

    override fun add(speechKit: SpeechKit?): Flow<ResultUC<SpeechKit>> {
        TODO("Not yet implemented")
    }

    override fun update(speechKit: SpeechKit): Flow<ResultUC<SpeechKit>> {
        TODO("Not yet implemented")
    }
}