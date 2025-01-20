package com.example.framework.room.source

import android.util.Log
import com.example.data.entity.ExerciseImpl
import com.example.data.entity.RingImpl
import com.example.data.entity.SpeechKitImpl
import com.example.data.source.room.RingSource
import com.example.framework.room.db.ring.RingDao
import com.example.framework.room.db.ring.RingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RingSourceImpl @Inject constructor(
    private val exerciseSource: ExerciseSourceImpl,
    private val speechKitSource: SpeechKitSourceImpl,
    private val dao: RingDao
): RingSource {
    override fun get(id: Long): Flow<RingImpl>  = dao.get(id).map { it.toRing() }

    override fun gets(trainingId: Long): Flow<List<RingImpl>> =
        dao.gets(trainingId).map { list-> list.map { it.toRing() } }

    override fun del(ring: RingImpl) {
        ring.exercise.forEach { exerciseSource.del(it as ExerciseImpl) }
        ring.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        dao.del(ring.idRing)
    }

    override fun addCopy(ring: RingImpl): Flow<List<RingImpl>> {
        if (ring.trainingId > 0) {
            val idSpeechKit =
                (speechKitSource.add(ring.speech as SpeechKitImpl) as StateFlow).value.idSpeechKit
            val ringId = dao.add(toRingTable(ring).copy(speechId = idSpeechKit))
            if (ring.exercise.isNotEmpty()) {
                ring.exercise.forEach { exercise ->
                    exerciseSource.addCopy((exercise as ExerciseImpl).copy(ringId = ringId))
                }
            } else {
                exerciseSource.addCopy(createExerciseImpl(ringId = ringId))
            }
        } else { Log.d("KDS", "The value is not defined TRAINING_ID")}
        return gets(ring.trainingId)
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
    private fun createExerciseImpl(ringId: Long) = ExerciseImpl(
        idExercise = 0,
        roundId = 0,
        ringId = ringId,
        activityId = 1,
        idView = 1,
        activity = null,
        speech = null,
        speechId = 0,
        sets =  emptyList(),
    )
}