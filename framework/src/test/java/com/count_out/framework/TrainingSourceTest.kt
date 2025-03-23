package com.count_out.framework

import android.content.Context
import androidx.multidex.BuildConfig
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.TrainingImplD
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.framework.room.AppDataBase
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.source.TrainingSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.RealObject
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class TrainingSourceTest {

    private lateinit var dao: TrainingDao
    private lateinit var db: AppDataBase
    private lateinit var trainingSource: TrainingSource
    private val roundSource = mock< RoundSource>()
    private val ringSource = mock< RingSource>()
    private val speechKitSource = mock< SpeechKitSource>()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.trainingDao()
        trainingSource = TrainingSourceImpl(dao, roundSource, ringSource, speechKitSource)
    }
    @After
    @Throws(IOException::class)
    fun closeDb() { db.close() }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddTrainings() = runTest {
        val expected = createTraining(id = 1)
        val training = createTraining()
        val trainingId = trainingSource.copy(training)
        val resultGet = trainingSource.get(training.copy(idTraining = trainingId)).first()
        Assert.assertEquals(expected, resultGet)
    }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testGetTrainings() = runTest {
//        val localPosts = listOf(PostEntity(1, 1, "title", "body"))
//        val expectedPosts = listOf(Post(1, 1, "title", "body"))
//        whenever(dao.getTrainingsRel()).thenReturn(flowOf(localPosts))
//        val result = trainingSource.gets().first()
//        Assert.assertEquals(expectedPosts, result)
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
    fun createTraining(id: Long =0): TrainingImplD{
        return TrainingImplD(
            idTraining = id,
            name = "Test 1",
            amountActivity = 0,
            isSelected = false,
            speechId = 0,
            speech = SpeechKitImplD(beforeEnd = SpeechImplD(), beforeStart = SpeechImplD(), afterStart = SpeechImplD(), afterEnd = SpeechImplD()),
            rings = emptyList(),
            rounds = emptyList(),
        )
    }
}