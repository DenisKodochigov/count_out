package com.count_out.presentation.trainings

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.use_case.plans.CopyTrainingUC
import com.count_out.domain.use_case.plans.DeleteTrainingUC
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.SelectTrainingUC
import com.count_out.domain.use_case.plans.UpdateTrainingUC
import com.count_out.domain.use_case.speech.UpdateSpeechKitUC
import com.count_out.presentation.models.SpeechImplP
import com.count_out.presentation.models.SpeechKitImplP
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.plans.PlansEvent
import com.count_out.presentation.screens.plans.PlansState
import com.count_out.presentation.screens.plans.PlansViewModel
import com.count_out.presentation.screens.prime.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TrainingsViewModelToUCTest {

    private val training1 = TrainingImplP(idTraining = 1)
    private val training2 = TrainingImplP(idTraining = 2)
    val listTraining = mutableListOf(training1,training2)

    companion object{
        lateinit var viewModel: PlansViewModel
        val copyTrainingUC = mock<CopyTrainingUC>()
        val delTrainingUC = mock<DeleteTrainingUC>()
        val getTrainingsUC = mock<GetTrainingsUC>()
        val updateTrainingUC = mock<UpdateTrainingUC>()
        val selectTrainingUC = mock<SelectTrainingUC>()
        val updateSpeechKitUC = mock<UpdateSpeechKitUC>()

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            Dispatchers.setMain(StandardTestDispatcher())  //UnconfinedTestDispatcher  StandardTestDispatcher
            viewModel = PlansViewModel(
                copyTrainingUC, delTrainingUC,getTrainingsUC,updateTrainingUC, selectTrainingUC, updateSpeechKitUC)
        }
        @JvmStatic
        @AfterAll
        fun afterAll() { Dispatchers.resetMain() }
    }

    @Test @Order(1)
    fun testGetsTrainingsSubmitEventToScreenState() = runTest {
        val exceptionScreenState = ScreenState.Success(dataState = PlansState(trainings = listTraining))
        whenever(getTrainingsUC.execute(GetTrainingsUC.Request))
            .thenReturn(flowOf(ResultUC.Success( GetTrainingsUC.Response(listTraining))))
        viewModel.submitEvent(PlansEvent.Gets)
        var actual = viewModel.screenState.value
        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
        Assertions.assertEquals(exceptionScreenState, actual)
    }
    @Test @Order(2)
    fun testCopyTrainingsSubmitEventToScreenState() = runTest {
        whenever(copyTrainingUC.execute(CopyTrainingUC.Request(training1)))
            .thenReturn(flowOf(ResultUC.Success( CopyTrainingUC.Response(listTraining))))
        viewModel.submitEvent(PlansEvent.Copy(training1))
        listTraining.add(training1)
        val exceptionScreenState = ScreenState.Success(dataState = PlansState(trainings = listTraining))
        var actual = viewModel.screenState.value
        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
        Assertions.assertEquals(exceptionScreenState, actual)
    }

    @Test @Order(3)
    fun testDelTrainingsSubmitEventToScreenState() = runTest {
        listTraining.remove(training1)
        whenever(delTrainingUC.execute(DeleteTrainingUC.Request(training1)))
            .thenReturn(flowOf(ResultUC.Success( DeleteTrainingUC.Response(listTraining))))
        viewModel.submitEvent(PlansEvent.Del(training1))
        val exceptionScreenState = ScreenState.Success(dataState = PlansState(trainings = listTraining))
        var actual = viewModel.screenState.value
        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
        Assertions.assertEquals(exceptionScreenState, actual)
    }
    @Test @Order(4)
    fun testUpdateTrainingsSubmitEventToScreenState() = runTest {
        val updatedTraining = training1.copy(name = "New")
        val exceptionScreenState = ScreenState.Success(dataState = PlansState( trainings = listTraining))
        whenever(updateTrainingUC.execute(UpdateTrainingUC.Request(training1)))
            .thenReturn(flowOf(ResultUC.Success( UpdateTrainingUC.Response(listTraining))))
        viewModel.submitEvent(PlansEvent.Update(training1))
        var actual = viewModel.screenState.value
        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
        Assertions.assertEquals(exceptionScreenState, actual,)
    }
    @Test @Order(5)
    fun testSelectedTrainingsSubmitEventToScreenState() = runTest {
        val exceptionScreenState = ScreenState.Success(dataState = PlansState( selectedId = 1))
        whenever(selectTrainingUC.execute(SelectTrainingUC.Request(training1)))
            .thenReturn(flowOf(ResultUC.Success( SelectTrainingUC.Response(1))))
        viewModel.submitEvent(PlansEvent.Select(training1))
        var actual = viewModel.screenState.value
        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
        Assertions.assertEquals(exceptionScreenState, actual,)
    }
    @Test @Order(6)
    fun testUpdateSpeechTrainingsSubmitEventToScreenState() = runTest {
        val speechKit = SpeechKitImplP(
            idSpeechKit = 1,
            beforeStart = SpeechImplP(idSpeech = 1, message = "beforeStart"),
            afterStart = SpeechImplP(idSpeech = 2, message = "beforeStart"),
            beforeEnd = SpeechImplP(idSpeech = 3, message = "beforeStart"),
            afterEnd = SpeechImplP(idSpeech = 4, message = "beforeStart"),
        )
        val exceptionScreenState = ScreenState.Success(dataState = PlansState( selectedId = 1))
        whenever(updateSpeechKitUC.execute(UpdateSpeechKitUC.Request(speechKit)))
            .thenReturn(flowOf(ResultUC.Success( UpdateSpeechKitUC.Response(speechKit))))
        viewModel.submitEvent(PlansEvent.UpdateSpeech(speechKit))
        var actual = viewModel.screenState.value
        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
        Assertions.assertEquals(exceptionScreenState, actual,)
    }
}


//    @BeforeEach
//    fun beforeEachTest() {
////        println("################### before ###################")
//        Dispatchers.setMain(UnconfinedTestDispatcher())
//        viewModel = TrainingsViewModel(copyTrainingUC, delTrainingUC,getTrainingsUC,selectTrainingUC, updateSpeechUC)
//    }
//    @AfterEach
//    fun afterEachTest(){
////        println("################### after ###################")
//        Dispatchers.resetMain() }


//@Test
//fun testSelectedTrainingsSubmitEventToScreenState() = runTest {
////        print("################### testSelectedTrainingsSubmitEventToScreenState ################### ")
//    val exceptionScreenState = ScreenState.Success(dataState = TrainingsState( selectedId = 1))
//    whenever(selectTrainingUC.execute(SelectTrainingUC.Request(training1)))
//        .thenReturn(flowOf(ResultUC.Success( SelectTrainingUC.Response(1))))
//    TrainingsViewModelTest.Companion.viewModel.submitEvent(TrainingsEvent.Select(training1))
//    Thread.sleep(1000)
//    var result = "true"
//    Assertions.assertEquals(exceptionScreenState, TrainingsViewModelTest.Companion.viewModel.screenState.value,{ result = "false"; result })
////        println(result)
//}