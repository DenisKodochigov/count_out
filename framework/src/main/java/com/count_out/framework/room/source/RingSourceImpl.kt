package com.count_out.framework.room.source

import android.util.Log
import com.count_out.data.models.ExerciseImpl
import com.count_out.data.models.RingImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.domain.entity.workout.Ring
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RingSourceImpl @Inject constructor(
    private val dao: RingDao,
    private val exerciseSource: ExerciseSource,
    private val speechKitSource: SpeechKitSource,
): RingSource {
    override fun get(ring: Ring): Flow<Ring>  = dao.get(ring.idRing).map { it.toRing() }

    override fun gets(trainingId: Long): Flow<List<Ring>> =
        dao.gets(trainingId).map { list-> list.map { it.toRing() } }

    override fun copy(ring: Ring): Long {
        var ringId = 0L
        if (ring.trainingId > 0) {
            val speechId = speechKitSource.copy(ring.speech?.let{ it as SpeechKitImpl } ?: SpeechKitImpl() )
            ringId = dao.add(toRingTable(ring as RingImpl).copy(speechId = speechId))
            if (ring.exercise.isNotEmpty()) {
                ring.exercise.forEach { exercise ->
                    exerciseSource.copy((exercise as ExerciseImpl).copy(ringId = ringId))
                }
            } else {exerciseSource.copy(ExerciseImpl().copy(ringId = ringId)) }
        } else { Log.d("KDS", "The value is not defined TRAINING_ID") }
        return ringId
    }
    override fun del(ring: Ring) {
        ring.exercise.forEach { exerciseSource.del(it as ExerciseImpl) }
        ring.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(ring.idRing)
    }
    override fun update(ring: Ring) {
        ring.exercise.forEach { exerciseSource.update(it as ExerciseImpl) }
        ring.speech?.let { speechKitSource.update(it as SpeechKitImpl) }
        dao.update(toRingTable(ring as RingImpl))
    }

    private fun toRingTable(ring: RingImpl) = RingTable(
        trainingId = ring.trainingId,
        name = ring.name,
        countRing = ring.countRing,
        speechId = ring.speechId,
    )
}