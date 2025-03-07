package com.example.count_out.data.repository

import com.example.count_out.domain.repository.trainings.RoundRepo
import com.example.count_out.data.source.room.RoundSource
import com.example.count_out.entity.workout.Round
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoundRepoImpl @Inject constructor(private val roundSource: RoundSource): RoundRepo {
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