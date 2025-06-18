package com.count_out.framework.room.source

import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechKitSource: SpeechKitSourceImpl,
    private val setDao: SetDao): SetSource {

    override fun get(item: SetImplD): Flow<SetImplD?> = setDao.get(item.idSet).map { it?.let { it1-> it1.toSet() } ?: null}

    override fun gets(exerciseId: Long): Flow<List<SetImplD>> {
        return setDao.gets(exerciseId).map { list-> list.map{ it.toSet()} } }

    override fun copy(item: SetImplD): Long {
        val speechId = speechKitSource.copyValue(item.speech?.let{ it as SpeechKitImplD } ?: SpeechKitImplD()) ?: 0L
        return setDao.add(toSetTable(item).copy(speechId = speechId)) }

    override fun del(item: SetImplD) {
        item.speech?.let { speechKitSource.del(SpeechKitImplD(it)) }
        setDao.del(item.idSet)
    }

    override fun update(item: SetImplD) {
        item.speech?.let { speechKitSource.update(SpeechKitImplD(it)) }
        setDao.update(toSetTable(item, item.idSet))
    }

    private fun toSetTable(set: SetImplD, idSet: Long = 0) = SetTable(
        idSet = idSet,
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