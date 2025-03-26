package com.count_out.framework

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.ParameterImpl
import com.count_out.data.models.RoundImpl
import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.TrainingImplD
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.data.source.room.SpeechSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.RoundType
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.framework.room.AppDataBase
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.source.ExerciseSourceImpl
import com.count_out.framework.room.source.RingSourceImpl
import com.count_out.framework.room.source.RoundSourceImpl
import com.count_out.framework.room.source.SetSourceImpl
import com.count_out.framework.room.source.SpeechKitSourceImpl
import com.count_out.framework.room.source.SpeechSourceImpl
import com.count_out.framework.room.source.TrainingSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TrainingDaoTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher ()
    private lateinit var dao: TrainingDao
    private lateinit var db: AppDataBase
    private lateinit var trainingSource: TrainingSource
    private lateinit var speechKitSource: SpeechKitSource
    private lateinit var speechSource: SpeechSource
    private lateinit var exerciseSource: ExerciseSource
    private lateinit var roundSource: RoundSource
    private lateinit var ringSource: RingSource
    private lateinit var setSource: SetSource
    private lateinit var expected: TrainingImplD
    private lateinit var training: TrainingImplD

    // InstrumentationRegistry.getInstrumentation().context,
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun createDb() {
        Dispatchers.setMain(testDispatcher)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.trainingDao()
        speechSource = SpeechSourceImpl(db.speechDao())
        speechKitSource = SpeechKitSourceImpl(speechSource, db.speechKitDao())
        setSource = SetSourceImpl(speechKitSource, db.setDao())
        exerciseSource = ExerciseSourceImpl(db.exerciseDao(), setSource, speechKitSource)
        ringSource = RingSourceImpl(db.ringDao(), exerciseSource, speechKitSource)
        roundSource = RoundSourceImpl(db.roundDao(), exerciseSource, speechKitSource)
        trainingSource = TrainingSourceImpl(dao, roundSource, ringSource, speechKitSource)

        expected = createExpectedTraining()
        training = createTraining()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddTrainings() =  runTest {
        val trainingId = trainingSource.copy(training)
        training = training.copy(idTraining = trainingId)
        var resultGet = trainingSource.get(training).first()
        Assert.assertEquals( "Error create training!!!",expected, resultGet)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testUpdateTrainings() = runTest {
        delay(2000)
        training = expected.copy(name = "Test Update")
        trainingSource.update(training)
        val resultGet = trainingSource.get(training).first()
        Assert.assertEquals( "Error update training!!!",training, resultGet)
    }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testDeleteTrainings() = runTest {
//        delay(2000)
//        trainingSource.del(training)
//        val resultGet = trainingSource.get(training).first()
//        Assert.assertNull(resultGet)
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
        rounds = emptyList(),)
    }
    fun createExpectedTraining(): TrainingImplD {
        return TrainingImplD(
            idTraining=1,
            name="Test 1",
            amountActivity=3,
            rounds= listOf(RoundImpl(
                idRound=1,
                trainingId=1,
                speechId=2,
                roundType= RoundType.WorkUp,
                speech=SpeechKitImplD(idSpeechKit=2, idBeforeStart=5, idAfterStart=6, idBeforeEnd=7, idAfterEnd=8,
                    beforeStart=SpeechImplD(idSpeech=5, message="", duration=0, addMessage=""),
                    afterStart=SpeechImplD(idSpeech=6, message="", duration=0, addMessage=""),
                    beforeEnd=SpeechImplD(idSpeech=7, message="", duration=0, addMessage=""),
                    afterEnd=SpeechImplD(idSpeech=8, message="", duration=0, addMessage="")),
                exercise=listOf( ExerciseImplD(
                    idExercise=1, roundId=1, ringId=0, idView=0, activity=null, activityId=1, speechId=3,
                    speech=SpeechKitImplD(idSpeechKit=3, idBeforeStart=9, idAfterStart=10, idBeforeEnd=11, idAfterEnd=12,
                        beforeStart=SpeechImplD(idSpeech=9, message="", duration=0, addMessage=""),
                        afterStart=SpeechImplD(idSpeech=10, message="", duration=0, addMessage=""),
                        beforeEnd=SpeechImplD(idSpeech=11, message="", duration=0, addMessage=""),
                        afterEnd=SpeechImplD(idSpeech=12, message="", duration=0, addMessage="")),
                    sets=listOf( SetImplD(
                        idSet=1, name="Set 1", exerciseId=1, speechId=4,
                        speech=SpeechKitImplD(idSpeechKit=4, idBeforeStart=13, idAfterStart=14, idBeforeEnd=15, idAfterEnd=16,
                            beforeStart=SpeechImplD(idSpeech=13, message="", duration=0, addMessage=""),
                            afterStart=SpeechImplD(idSpeech=14, message="", duration=0, addMessage=""),
                            beforeEnd=SpeechImplD(idSpeech=15, message="", duration=0, addMessage=""),
                            afterEnd=SpeechImplD(idSpeech=16, message="", duration=0, addMessage="")),
                        goal= Goal.Count,
                        weight=ParameterImpl(value=1.0, unit=Units.KG),
                        distance=ParameterImpl(value=1.0, unit=Units.MT),
                        duration=ParameterImpl(value=1.0, unit=Units.S),
                        reps=10,
                        intensity=Zone.Medium,
                        intervalReps=1.0,
                        intervalDown=0,
                        groupCount="",
                        rest=ParameterImpl(value=1.0, unit=Units.S))
                    ),
                    amountSet=1,
                    duration=11)
                ),
                amount=1,
                duration=ParameterImpl(value=0.18333333333333332, unit=Units.M)),
                RoundImpl(
                    idRound=2,
                    trainingId=1,
                    speechId=5,
                    roundType= RoundType.WorkOut,
                    speech=SpeechKitImplD(
                        idSpeechKit=5, idBeforeStart=17, idAfterStart=18, idBeforeEnd=19, idAfterEnd=20,
                        beforeStart=SpeechImplD(idSpeech=17, message="", duration=0, addMessage=""),
                        afterStart=SpeechImplD(idSpeech=18, message="", duration=0, addMessage=""),
                        beforeEnd=SpeechImplD(idSpeech=19, message="", duration=0, addMessage=""),
                        afterEnd=SpeechImplD(idSpeech=20, message="", duration=0, addMessage="")),
                    exercise=listOf(ExerciseImplD(
                        idExercise=2, roundId=2,
                        ringId=0,
                        idView=0,
                        activity=null,
                        activityId=1,
                        speechId=6,
                        speech=SpeechKitImplD(
                            idSpeechKit=6, idBeforeStart=21, idAfterStart=22, idBeforeEnd=23, idAfterEnd=24,
                            beforeStart=SpeechImplD(idSpeech=21, message="", duration=0, addMessage=""),
                            afterStart=SpeechImplD(idSpeech=22, message="", duration=0, addMessage=""),
                            beforeEnd=SpeechImplD(idSpeech=23, message="", duration=0, addMessage=""),
                            afterEnd=SpeechImplD(idSpeech=24, message="", duration=0, addMessage="")),
                        sets=listOf( SetImplD(
                            idSet=2, name="Set 1", exerciseId=2, speechId=7,
                            speech=SpeechKitImplD(
                                idSpeechKit=7, idBeforeStart=25, idAfterStart=26, idBeforeEnd=27, idAfterEnd=28,
                                beforeStart=SpeechImplD(idSpeech=25, message="", duration=0, addMessage=""),
                                afterStart=SpeechImplD(idSpeech=26, message="", duration=0, addMessage=""),
                                beforeEnd=SpeechImplD(idSpeech=27, message="", duration=0, addMessage=""),
                                afterEnd=SpeechImplD(idSpeech=28, message="", duration=0, addMessage="")),
                            goal=Goal.Count,
                            weight=ParameterImpl(value=1.0, unit=Units.KG),
                            distance=ParameterImpl(value=1.0, unit=Units.MT),
                            duration=ParameterImpl(value=1.0, unit=Units.S),
                            reps=10,
                            intensity=Zone.Medium,
                            intervalReps=1.0,
                            intervalDown=0, 
                            groupCount="",
                            rest=ParameterImpl(value=1.0, unit=Units.S))
                        ),
                        amountSet=1,
                        duration=11)
                    ),
                    amount=1,
                    duration=ParameterImpl(value=0.18333333333333332, unit=Units.M)),
                RoundImpl(
                    idRound=3,
                    trainingId=1,
                    speechId=8,
                    roundType= RoundType.WorkDown,
                    speech=SpeechKitImplD(idSpeechKit=8, idBeforeStart=29, idAfterStart=30, idBeforeEnd=31, idAfterEnd=32,
                        beforeStart=SpeechImplD(idSpeech=29, message="", duration=0, addMessage=""),
                        afterStart=SpeechImplD(idSpeech=30, message="", duration=0, addMessage=""),
                        beforeEnd=SpeechImplD(idSpeech=31, message="", duration=0, addMessage=""),
                        afterEnd=SpeechImplD(idSpeech=32, message="", duration=0, addMessage="")),
                    exercise=listOf(ExerciseImplD(
                        idExercise=3,
                        roundId=3,
                        ringId=0,
                        idView=0,
                        activity=null,
                        activityId=1,
                        speechId=9,
                        speech=SpeechKitImplD(
                            idSpeechKit=9,
                            idBeforeStart=33,
                            idAfterStart=34,
                            idBeforeEnd=35,
                            idAfterEnd=36,
                            beforeStart=SpeechImplD(idSpeech=33, message="", duration=0, addMessage=""),
                            afterStart=SpeechImplD(idSpeech=34, message="", duration=0, addMessage=""),
                            beforeEnd=SpeechImplD(idSpeech=35, message="", duration=0, addMessage=""),
                            afterEnd=SpeechImplD(idSpeech=36, message="", duration=0, addMessage="")),
                        sets=listOf( SetImplD(
                            idSet=3,
                            name="Set 1",
                            exerciseId=3,
                            speechId=10,
                            speech=SpeechKitImplD(idSpeechKit=10, idBeforeStart=37, idAfterStart=38, idBeforeEnd=39, idAfterEnd=40,
                                beforeStart=SpeechImplD(idSpeech=37, message="", duration=0, addMessage=""),
                                afterStart=SpeechImplD(idSpeech=38, message="", duration=0, addMessage=""),
                                beforeEnd=SpeechImplD(idSpeech=39, message="", duration=0, addMessage=""),
                                afterEnd=SpeechImplD(idSpeech=40, message="", duration=0, addMessage="")),
                            goal=Goal.Count, weight=ParameterImpl(value=1.0, unit=Units.KG),
                            distance=ParameterImpl(value=1.0, unit=Units.MT),
                            duration=ParameterImpl(value=1.0, unit=Units.S),
                            reps=10,
                            intensity=Zone.Medium,
                            intervalReps=1.0,
                            intervalDown=0,
                            groupCount="",
                            rest=ParameterImpl(value=1.0, unit=Units.S))
                        ),
                        amountSet=1,
                        duration=11)
                    ),
                    amount=1,
                    duration=ParameterImpl(value=0.18333333333333332, unit=Units.M))
            ),
            rings=emptyList(),
            isSelected=false,
            speechId=1,
            speech=SpeechKitImplD(idSpeechKit=1, idBeforeStart=1, idAfterStart=2, idBeforeEnd=3, idAfterEnd=4,
                beforeStart=SpeechImplD(idSpeech=1, message="", duration=0, addMessage=""),
                afterStart=SpeechImplD(idSpeech=2, message="", duration=0, addMessage=""),
                beforeEnd=SpeechImplD(idSpeech=3, message="", duration=0, addMessage=""),
                afterEnd=SpeechImplD(idSpeech=4, message="", duration=0, addMessage="")))
    }
}