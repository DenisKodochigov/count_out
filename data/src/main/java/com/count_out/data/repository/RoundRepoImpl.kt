package com.count_out.data.repository

import com.count_out.data.source.room.RoundSource
import com.count_out.entity.entity.workout.Round
import com.count_out.domain.repository.trainings.RoundRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoundRepoImpl @Inject constructor(private val ringSource: RoundSource): RoundRepo {
    override fun get(id: Long): Flow<Round> {
        TODO("Not yet implemented")
    }

    override fun gets(trainingId: Long): Flow<List<Round>> {
        TODO("Not yet implemented")
    }

    override fun update(round: Round): Flow<Round> {
        TODO("Not yet implemented")
    }
}