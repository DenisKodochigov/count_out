package com.count_out.framework

import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
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
    fun testAddTrainings() = runTest {
        val training = createTraining()
        val trainingId = trainingSource.copy(training)
        whenever(dao.add(TrainingTable(training))).thenReturn(1)
        Assert.assertEquals(1, trainingId)
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
            speech = SpeechKitImplD(
                beforeEnd = SpeechImplD(),
                beforeStart = SpeechImplD(),
                afterStart = SpeechImplD(),
                afterEnd = SpeechImplD()
            ),
            rings = emptyList(),
            rounds = emptyList(),
        )
    }
}