package com.example.count_out.framework.room.source

import android.util.Log
import com.example.count_out.data.source.room.ExerciseSource
import com.example.count_out.data.source.room.RingSource
import com.example.count_out.data.source.room.SpeechKitSource
import com.example.count_out.entity.models.ExerciseImpl
import com.example.count_out.entity.models.RingImpl
import com.example.count_out.entity.models.SpeechKitImpl
import com.example.count_out.entity.workout.Ring
import com.example.count_out.framework.room.db.ring.RingDao
import com.example.count_out.framework.room.db.ring.RingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RingSourceImpl @Inject constructor(
    private val dao: RingDao,
    private val exerciseSource: ExerciseSource,
    private val speechKitSource: SpeechKitSource,
): RingSource {
    override fun get(id: Long): Flow<RingImpl>  = flow { emit (dao.get(id).toRing())}

    override fun gets(trainingId: Long): Flow<List<Ring>> =
        flow { emit (dao.gets(trainingId).map { it.toRing() } ) }

    override fun del(ring: Ring) {
        ring.exercise.forEach { exerciseSource.del(it as ExerciseImpl) }
        ring.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(ring.idRing)
    }

    override fun add(trainingId: Long): Flow<List<Ring>> {
        val result = if (trainingId > 0) {
            val ringId = dao.add(
                RingTable(speechId = (speechKitSource.add(null)))
            )
            exerciseSource.add(ringId = ringId)
            gets(trainingId)
        } else {
            Log.d("KDS", "The value is not defined TRAINING_ID")
            flow { emit( emptyList()) }
        }
        return result
    }

    override fun copy(ring: Ring): Flow<List<Ring>> {
        val result = if (ring.trainingId > 0) {
            val idSpeechKit = (speechKitSource.copy( ring.speech as SpeechKitImpl) )
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
    override fun update(ring: Ring): Flow<Ring> {
        dao.update(toRingTable(ring).copy(idRing = ring.idRing))
        return get(ring.idRing)
    }

    private fun toRingTable(ring: Ring) = RingTable(
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