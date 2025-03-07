package com.example.count_out.data.repository

import com.example.count_out.data.source.room.SetSource
import com.example.count_out.domain.repository.trainings.SetRepo
import com.example.count_out.entity.workout.ActionWithSet
import com.example.count_out.entity.workout.Set
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(private val setSource: SetSource): SetRepo {
    override fun add( item: ActionWithSet): Flow<List<Set>> = setSource.add(item)
    override fun copy( item: ActionWithSet): Flow<List<Set>> = setSource.copy(item)

    override fun gets(exerciseId: Long): Flow<List<Set>> = setSource.gets(exerciseId = exerciseId)

    override fun get(id: Long): Flow<Set> = setSource.get(id)

    override fun del( item: ActionWithSet) = setSource.del(item)

    override fun update( item: ActionWithSet): Flow<Set> = setSource.update(item)
}