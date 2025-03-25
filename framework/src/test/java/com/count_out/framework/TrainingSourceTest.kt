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
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

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
        Assert.assertEquals(1, trainingId)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testGetTraining() = runTest {
        val training = createTraining(id = 1)
        whenever(dao.getTrainingRel(training.idTraining)).thenReturn(
            flowOf( TrainingRel(
                training = TrainingTable(training),
                rounds = emptyList(),
                rings =  emptyList(),
                speechKit = null
            ))
        )
        val trainingId = trainingSource.get(training).first()
        Assert.assertEquals(training, trainingId)
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
        Assert.assertEquals(list, result)
    }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testGetTrainings() = runTest {
////        val expectedTraining = createTraining(id = 1)
//        val training = createTraining(id = 1)
//        whenever(dao.getTrainingRel(training.idTraining)).thenReturn(flowOf(TrainingRel(
//            training = TrainingTable(training),
//            rounds = emptyList(),
//            rings = emptyList(),
//            speechKit = null
//        )))
//        val resultGet = trainingSource.get(training).first()
//        Assert.assertEquals(training, resultGet)
//    }

    //
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testAddUsers() = runTest {
//        val localPosts = listOf(PostEntity(1, 1, "title", "body"))
//        val posts = listOf(Post(1, 1, "title", "body"))
//        postDataSource.addPosts(posts)
//        verify(postDao).insertPosts(localPosts)
//    }
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