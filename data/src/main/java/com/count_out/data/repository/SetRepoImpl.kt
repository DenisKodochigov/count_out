package com.count_out.data.repository

import com.count_out.data.models.SetImpl
import com.count_out.data.source.room.SetSource
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.repository.trainings.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(private val setSource: SetSource): SetRepo {

    override fun gets(exerciseId: Long): Flow<List<Set>> = setSource.gets(exerciseId = exerciseId)

    override fun get(set: Set): Flow<Set> = setSource.get(set as SetImpl)

    override fun copy(set: Set): Flow<List<SetImpl>> {
        setSource.copy(set as SetImpl)
        return setSource.gets(set.exerciseId)
    }

    override fun del(set: Set): Flow<Set> {
        setSource.del(set as SetImpl)
        return setSource.get(set)
    }

    override fun update(set: Set): Flow<Set> {
        setSource.update(set as SetImpl)
        return setSource.get(set)
    }
}