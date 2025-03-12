package com.count_out.framework.room.source

import com.count_out.data.models.SetImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechKitSource: SpeechKitSource,
    private val setDao: SetDao): SetSource {

    override fun get(item: SetImpl): Flow<SetImpl> = setDao.get(item.idSet).map { it.toSet() }

    override fun gets(exerciseId: Long): Flow<List<SetImpl>> {
        return setDao.gets(exerciseId).map { list-> list.map{ it.toSet()} } }

    override fun copy(item: SetImpl): Long {
        val speechId = speechKitSource.copy(item.speech?.let{ it as SpeechKitImpl } ?: SpeechKitImpl() )
        return setDao.add(toSetTable(item).copy(speechId = speechId)) }

    override fun del(item: SetImpl) {
        item.speech?.let { speechKitSource.del(it) }
        setDao.del(item.idSet)
    }

    override fun update(item: SetImpl) {
        item.speech?.let { speechKitSource.update(it) }
        setDao.update(toSetTable(item))
    }

    private fun toSetTable(set: SetImpl) = SetTable(
        idSet = set.idSet,
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