package com.count_out.data.repository

import com.count_out.data.models.SetImpl
import com.count_out.data.source.room.SetSource
import com.count_out.domain.entity.ActionWithSet
import com.count_out.domain.entity.Set
import com.count_out.domain.repository.trainings.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(private val setSource: SetSource): SetRepo {
    override fun add( item:ActionWithSet): Flow<List<SetImpl>> = setSource.add(item)
    override fun copy( item:ActionWithSet): Flow<List<SetImpl>> = setSource.copy(item)

    override fun gets(exerciseId: Long): Flow<List<Set>> = setSource.gets(exerciseId = exerciseId)

    override fun get(id: Long): Flow<Set> = setSource.get(id)

    override fun del( item:ActionWithSet) = setSource.del(item)

    override fun update( item:ActionWithSet): Flow<Set> = setSource.update(item)
}