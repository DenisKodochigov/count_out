package com.count_out.data.repository

import com.count_out.data.models.SetImplD
import com.count_out.data.source.room.SetSource
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.repository.plans.SetRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class SetRepoImpl @Inject constructor(private val setSource: SetSource): SetRepo {

    override fun gets(exerciseId: Long): Flow<List<Set>> = setSource.gets(exerciseId = exerciseId)

    override fun get(set: Set): Flow<Set> = setSource.get(SetImplD(set)).filterNotNull()

    override fun copy(set: Set): Flow<List<Set>> {
        val setD = SetImplD(set)
        setSource.copy(setD)
        return setSource.gets(set.exerciseId)
    }

    override fun del(set: Set): Flow<Set> {
        val setD = SetImplD(set)
        setSource.del(setD)
        return setSource.get(setD).filterNotNull()
    }

    override fun update(set: Set): Flow<Set> {
        val setD = SetImplD(set)
        setSource.update(setD)
        return setSource.get(setD).filterNotNull()
    }
}