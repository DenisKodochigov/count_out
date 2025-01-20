package com.example.framework.room.source

import com.example.data.entity.SetImpl
import com.example.data.source.room.SetSource
import com.example.framework.room.db.set.SetDao
import com.example.framework.room.db.set.SetTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechKitSource: SpeechKitSourceImpl,
    private val setDao: SetDao): SetSource {

    override fun addCopy(set: SetImpl): Flow<List<SetImpl>> {
        /**
         *  Без idExersice создавать set нельзя.
         *  Процедура копирует SET, если входной SET пустой, то создается новый.
        */
        if (set.exerciseId > 0L) {
            if (set.idSet > 0) {
                val idSpeechKit = set.speech?.let {
                    (speechKitSource.add(it) as StateFlow).value.idSpeechKit } ?: 0
                setDao.add(toSetTable(set.copy(speechId = idSpeechKit)))
            }
        }
        return gets(set.exerciseId)
    }

    override fun gets(exerciseId: Long): Flow<List<SetImpl>> {
        return setDao.gets(exerciseId).map { list-> list.map{ it.toSet()} } }

    override fun get(id: Long): Flow<SetImpl> = setDao.getRel(id).map { it.toSet() }

    override fun del(set: SetImpl) {
        set.speech?.let { speechKitSource.del(it) }
        setDao.del(set.idSet)
    }

    override fun update(set: SetImpl): Flow<SetImpl> {
        setDao.update(toSetTable(set).copy(idSet = set.idSet))
        return get(set.idSet)
    }

    private fun toSetTable(set: SetImpl) = SetTable(
        name = set.name,
        speechId = set.speechId,
        goal = set.goal.ordinal,
        exerciseId = set.exerciseId,
        reps = set.reps,
        duration = set.duration,
        durationU = set.durationU.ordinal,
        distance = set.distance,
        distanceU = set.distanceU.ordinal,
        weight = set.weight,
        weightU = set.weightU.ordinal,
        intervalReps = set.intervalReps,
        intensity = set.intensity.ordinal,
        intervalDown = set.intervalDown,
        groupCount = set.groupCount,
        timeRest = set.timeRest,
        timeRestU = set.timeRestU.ordinal
    )
}