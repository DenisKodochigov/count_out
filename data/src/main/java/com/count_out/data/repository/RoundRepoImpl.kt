package com.count_out.data.repository

import com.count_out.data.models.RingImpl
import com.count_out.data.models.RoundImpl
import com.count_out.data.source.room.RoundSource
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.repository.trainings.RoundRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class RoundRepoImpl @Inject constructor(private val roundSource: RoundSource): RoundRepo {
    override fun get(round: Round): Flow<Round> {
        return roundSource.get(RoundImpl(round)).filterNotNull()
    }

    override fun gets(trainingId: Long): Flow<List<Round>> {
        return roundSource.gets(trainingId).filterNotNull()
    }

    override fun update(round: Round): Flow<Round> {
        roundSource.update(round as RoundImpl)
        return roundSource.get(round).filterNotNull()
    }

    override fun del(round: Round): Flow<List<Round>> {
        roundSource.del(round as RoundImpl)
        return roundSource.gets(round.trainingId).filterNotNull()
    }
}