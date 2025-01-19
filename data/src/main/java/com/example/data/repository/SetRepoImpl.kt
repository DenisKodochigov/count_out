package com.example.data.repository

import com.example.data.entity.SetImpl
import com.example.data.source.room.SetSource
import com.example.domain.entity.Set
import com.example.domain.repository.training.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(private val setSource: SetSource): SetRepo {
    override fun addCopy(set: Set): Flow<List<SetImpl>> = setSource.addCopy(set as SetImpl)

    override fun gets(exerciseId: Long): Flow<List<Set>> = setSource.gets(exerciseId = exerciseId)

    override fun get(id: Long): Flow<Set> = setSource.get(id)

    override fun del(set: Set) { setSource.del(set as SetImpl) }

    override fun update(set: Set): Flow<Set> = setSource.update(set as SetImpl)
}