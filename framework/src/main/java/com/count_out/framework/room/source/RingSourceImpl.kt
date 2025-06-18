package com.count_out.framework.room.source

import android.util.Log
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.RingImpl
import com.count_out.data.models.SpeechKitImplD
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
    private val speechKitSource: SpeechKitSourceImpl,
): RingSource {
    override fun get(ring: RingImpl): Flow<RingImpl?>  = dao.get(ring.idRing).map { it?.let { it1-> it1.toRing() } ?: null }

    override fun gets(trainingId: Long): Flow<List<RingImpl>> =
        dao.gets(trainingId).map { list-> list.map { it.toRing() } }

    override fun copy(ring: RingImpl): Long {
        var ringId = 0L
        if (ring.trainingId > 0) {
            val speechId = speechKitSource.copyValue(ring.speech?.let{ it as SpeechKitImplD } ?: SpeechKitImplD()) ?: 0L
            ringId = dao.add(toRingTable(ring).copy(speechId = speechId))
            if (ring.exercise.isNotEmpty()) {
                ring.exercise.forEach { exercise ->
                    exerciseSource.copy((exercise as ExerciseImplD).copy(ringId = ringId))
                }
            } else {exerciseSource.copy(ExerciseImplD().copy(ringId = ringId)) }
        } else { Log.d("KDS", "The value is not defined TRAINING_ID") }
        return ringId
    }
    override fun del(ring: RingImpl) {
        ring.exercise.forEach { exerciseSource.del(it as ExerciseImplD) }
        ring.speech?.let { speechKitSource.del(it as SpeechKitImplD) }
        dao.del(ring.idRing)
    }
    override fun update(ring: RingImpl) {
        ring.exercise.forEach { exerciseSource.update(it as ExerciseImplD) }
        ring.speech?.let { speechKitSource.update(it as SpeechKitImplD) }
        dao.update(toRingTable(ring))
    }

    private fun toRingTable(ring: RingImpl) = RingTable(
        trainingId = ring.trainingId,
        name = ring.name,
        countRing = ring.countRing,
        speechId = ring.speechId,
    )
}