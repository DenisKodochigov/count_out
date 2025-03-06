package com.count_out.framework.room.source

import android.util.Log
import com.count_out.data.models.ExerciseImpl
import com.count_out.data.models.RingImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RingSourceImpl @Inject constructor(
    private val dao: RingDao,
    private val exerciseSource: ExerciseSource,
    private val speechKitSource: SpeechKitSource,
): RingSource {
    override fun get(id: Long): Flow<RingImpl>  = dao.get(id).map { it.toRing() }

    override fun gets(trainingId: Long): Flow<List<RingImpl>> =
        dao.gets(trainingId).map { list-> list.map { it.toRing() } }

    override fun del(ring: RingImpl) {
        ring.exercise.forEach { exerciseSource.del(it as ExerciseImpl) }
        ring.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(ring.idRing)
    }

    override fun add(trainingId: Long): Flow<List<RingImpl>> {
        val result = if (trainingId > 0) {
            val ringId = dao.add(
                RingTable(speechId = com.count_out.domain.entity.workout.SpeechKit.idSpeechKit))
            exerciseSource.add(ringId = ringId)
            gets(trainingId)
        } else {
            Log.d("KDS", "The value is not defined TRAINING_ID")
            flow { emit( emptyList()) }
        }
        return result
    }

    override fun copy(ring: RingImpl): Flow<List<RingImpl>> {
        val result = if (ring.trainingId > 0) {
            val idSpeechKit =
                com.count_out.domain.entity.workout.SpeechKit.idSpeechKit
            val ringId = dao.add(toRingTable(ring).copy(speechId = idSpeechKit))
            if (ring.exercise.isNotEmpty()) {
                ring.exercise.forEach { exercise ->
                    exerciseSource.copy((exercise as ExerciseImpl).copy(ringId = ringId))
                }
            } else {
                exerciseSource.add(ringId = ringId)
            }
            gets(ring.trainingId)
        } else {
            Log.d("KDS", "The value is not defined TRAINING_ID")
            flow { emit(emptyList()) }
        }
        return result
    }
    override fun update(ring: RingImpl): Flow<RingImpl> {
        dao.update(toRingTable(ring).copy(idRing = ring.idRing))
        return get(ring.idRing)
    }

    private fun toRingTable(ring: RingImpl) = RingTable(
        trainingId = ring.trainingId,
        name = ring.name,
        countRing = ring.countRing,
        speechId = ring.speechId,
    )
//    private fun createExerciseImpl(ringId: Long) = ExerciseImpl(
//        idExercise = 0,
//        roundId = 0,
//        ringId = ringId,
//        activityId = 1,
//        idView = 1,
//        activity = null,
//        speech = null,
//        speechId = 0,
//        sets =  emptyList(),
//    )
}