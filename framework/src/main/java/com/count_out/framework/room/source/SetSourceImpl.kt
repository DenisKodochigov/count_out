package com.count_out.framework.room.source

import com.count_out.data.models.SetImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.domain.entity.workout.ActionWithSet
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechKitSource: SpeechKitSource,
    private val setDao: SetDao): SetSource {

    override fun add(item: com.count_out.domain.entity.workout.ActionWithSet): Flow<List<SetImpl>> {
         return if (com.count_out.domain.entity.workout.Set.exerciseId > 0L) {
            setDao.add(SetTable(speechId = speechKitSource.addL()))
            gets(com.count_out.domain.entity.workout.Set.exerciseId)
        } else flow { emit ( emptyList()) }
    }
    override fun copy(item: com.count_out.domain.entity.workout.ActionWithSet): Flow<List<SetImpl>> {
        return if (com.count_out.domain.entity.workout.Set.exerciseId > 0L) {
            if (com.count_out.domain.entity.workout.Set.idSet > 0) {
                val idSpeechKit = com.count_out.domain.entity.workout.Set.speech?.let {
                    speechKitSource.copyL(it as SpeechKitImpl) } ?: speechKitSource.addL()
                setDao.add(toSetTable((com.count_out.domain.entity.workout.ActionWithSet.set as SetImpl).copy(speechId = idSpeechKit)))
            }
            gets(com.count_out.domain.entity.workout.Set.exerciseId)
        } else flow { emit( emptyList()) }
    }
    override fun gets(exerciseId: Long): Flow<List<SetImpl>> {
        return setDao.gets(exerciseId).map { list-> list.map{ it.toSet()} } }

    override fun get(id: Long): Flow<SetImpl> = setDao.getRel(id).map { it.toSet() }

    override fun del(item: com.count_out.domain.entity.workout.ActionWithSet): Flow<List<SetImpl>> {
        com.count_out.domain.entity.workout.Set.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        setDao.del(com.count_out.domain.entity.workout.Set.idSet)
        return gets(com.count_out.domain.entity.workout.Set.exerciseId)
    }

    override fun update(item: com.count_out.domain.entity.workout.ActionWithSet): Flow<SetImpl> {
        setDao.update(toSetTable(com.count_out.domain.entity.workout.ActionWithSet.set as SetImpl).copy(idSet = com.count_out.domain.entity.workout.Set.idSet))
        return get(com.count_out.domain.entity.workout.Set.idSet)
    }

    private fun toSetTable(set: SetImpl) = SetTable(
        name = set.name,
        speechId = set.speechId,
        goal = set.goal.ordinal,
        exerciseId = set.exerciseId,
        reps = set.reps,
        duration = com.count_out.domain.entity.workout.Parameter.value,
        durationU = com.count_out.domain.entity.workout.Parameter.unit.ordinal,
        distance = com.count_out.domain.entity.workout.Parameter.value,
        distanceU = com.count_out.domain.entity.workout.Parameter.unit.ordinal,
        weight = com.count_out.domain.entity.workout.Parameter.value,
        weightU = com.count_out.domain.entity.workout.Parameter.unit.ordinal,
        intervalReps = set.intervalReps,
        intensity = set.intensity.ordinal,
        intervalDown = set.intervalDown,
        groupCount = set.groupCount,
        timeRest = com.count_out.domain.entity.workout.Parameter.value,
        timeRestU = com.count_out.domain.entity.workout.Parameter.unit.ordinal
    )
}