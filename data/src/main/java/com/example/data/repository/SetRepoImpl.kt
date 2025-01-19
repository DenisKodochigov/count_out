package com.example.data.repository

import com.example.data.entity.SetImpl
import com.example.data.source.room.SetSource
import com.example.domain.entity.Set
import com.example.domain.repository.training.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(private val setSource: SetSource): SetRepo {
    override fun add(set: Set): Flow<Set?> = setSource.add(set as SetImpl)

    override fun gets(exerciseId: Long): Flow<List<Set>> = setSource.gets(exerciseId = exerciseId)

    override fun get(id: Long): Flow<Set> = setSource.get(id)

    override fun copy(set: Set, newExerciseId: Long): Flow<Set> = setSource.copy(set as SetImpl, newExerciseId)

    override fun del(set: Set) { setSource.del(set as SetImpl) }

    override fun update(set: Set): Flow<Set> = setSource.update(set as SetImpl)
}