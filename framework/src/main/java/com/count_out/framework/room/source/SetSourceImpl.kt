package com.count_out.framework.room.source

import com.count_out.data.entity.SetImpl
import com.count_out.data.entity.SpeechKitImpl
import com.count_out.data.source.room.SetSource
import com.count_out.domain.entity.ActionWithSet
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechKitSource: SpeechKitSourceImpl,
    private val setDao: SetDao): SetSource {

    override fun add(item: ActionWithSet): Flow<List<SetImpl>> {
         return if (item.set.exerciseId > 0L) {
            setDao.add(SetTable(speechId = (speechKitSource.add() as StateFlow).value.idSpeechKit))
            gets(item.set.exerciseId)
        } else flow { emit ( emptyList()) }
    }
    override fun copy(item: ActionWithSet): Flow<List<SetImpl>> {
        return if (item.set.exerciseId > 0L) {
            if (item.set.idSet > 0) {
                val idSpeechKit = item.set.speech?.let {
                    (speechKitSource.copy(it as SpeechKitImpl) as StateFlow).value.idSpeechKit } ?: 0
                setDao.add(toSetTable((item.set as SetImpl).copy(speechId = idSpeechKit)))
            }
            gets(item.set.exerciseId)
        } else flow { emit( emptyList()) }
    }
    override fun gets(exerciseId: Long): Flow<List<SetImpl>> {
        return setDao.gets(exerciseId).map { list-> list.map{ it.toSet()} } }

    override fun get(id: Long): Flow<SetImpl> = setDao.getRel(id).map { it.toSet() }

    override fun del(item: ActionWithSet): Flow<List<SetImpl>> {
        item.set.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        setDao.del(item.set.idSet)
        return gets(item.set.exerciseId)
    }

    override fun update(item: ActionWithSet): Flow<SetImpl> {
        setDao.update(toSetTable(item.set as SetImpl).copy(idSet = item.set.idSet))
        return get(item.set.idSet)
    }

    private fun toSetTable(set: SetImpl) = SetTable(
        name = set.name,
        speechId = set.speechId,
        goal = set.goal.ordinal,
        exerciseId = set.exerciseId,
        reps = set.reps,
        duration = set.duration.value,
        durationU = set.duration.unit.ordinal,
        distance = set.distance.value,
        distanceU = set.distance.unit.ordinal,
        weight = set.weight.value,
        weightU = set.weight.unit.ordinal,
        intervalReps = set.intervalReps,
        intensity = set.intensity.ordinal,
        intervalDown = set.intervalDown,
        groupCount = set.groupCount,
        timeRest = set.rest.value,
        timeRestU = set.rest.unit.ordinal
    )
}