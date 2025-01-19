package com.example.data.repository

import com.example.data.source.room.RoundSource
import com.example.domain.entity.Round
import com.example.domain.repository.training.RoundRepo
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