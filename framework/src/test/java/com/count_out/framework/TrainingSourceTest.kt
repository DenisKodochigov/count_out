package com.count_out.framework

import com.count_out.data.models.TrainingImplD
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.framework.room.db.relation.TrainingRel
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.db.training.TrainingTable
import com.count_out.framework.room.source.TrainingSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Collections.emptyList

class TrainingSourceTest {
    private val dao = mock<TrainingDao>()
    private val roundSource = mock<RoundSource>()
    private val ringSource = mock<RingSource>()
    private val speechKitSource = mock<SpeechKitSource>()
    private val trainingSource = TrainingSourceImpl(dao, roundSource, ringSource,speechKitSource)

    @ExperimentalCoroutinesApi
    @Test
    fun testAddTraining() = runTest {
        val training = createTraining()
        whenever(dao.add(TrainingTable(training))).thenReturn(1L)
        val trainingId = trainingSource.copy(training)
        Assertions.assertEquals(1, trainingId)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testCopyTraining() = runTest {
        val expected = createTraining(1).copy(name = "Test 1 copy")
        whenever(dao.add(TrainingTable(expected))).thenReturn(2)
        whenever(dao.getTrainingRel(expected.idTraining)).thenReturn(
            flowOf( TrainingRel(
                training = TrainingTable(expected),
                rounds = emptyList(),
                rings =  emptyList(),
                speechKit = null
            ))
        )
        val trainingId = trainingSource.copy(expected)
        expected.copy()
        Assertions.assertEquals(1, trainingId)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetTraining() = runTest {
        val expected = createTraining(id = 1)
        whenever(dao.getTrainingRel(expected.idTraining)).thenReturn(
            flowOf( TrainingRel(
                training = TrainingTable(expected),
                rounds = emptyList(),
                rings =  emptyList(),
                speechKit = null
            ))
        )
        val training = trainingSource.get(expected).first()
        Assertions.assertEquals(expected, training)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetTrainings() = runTest {
        val list = listOf(createTraining(id = 1), createTraining(id = 2), createTraining(id = 3))
        whenever(dao.getTrainingsRel()).thenReturn(
            flowOf( listOf(
                TrainingRel(training = TrainingTable(list[0]), rounds = emptyList(), rings =  emptyList(), speechKit = null),
                TrainingRel(training = TrainingTable(list[1]), rounds = emptyList(), rings =  emptyList(), speechKit = null),
                TrainingRel(training = TrainingTable(list[2]), rounds = emptyList(), rings =  emptyList(), speechKit = null),
            ))
        )
        val result = trainingSource.gets().first()
        Assertions.assertEquals(list, result)
    }

    fun createTraining(id: Long =0): TrainingImplD {
        return TrainingImplD(
            idTraining = id,
            name = "Test 1",
            amountActivity = 0,
            isSelected = false,
            speechId = 0,
            speech = null,
            rings = emptyList(),
            rounds = emptyList(),
        )
    }
}