package com.count_out.domain.repo

import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.plans.DeleteTrainingUC
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.SelectTrainingUC
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.mockito.kotlin.mock

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SpeechRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

//    private val training1 = TrainingImplD(idTraining = 1)
//    private val training2 = TrainingImplD(idTraining = 2)
//    val listTraining = mutableListOf(training1,training2)

    companion object{
        val repo = mock<PlanRepo>()
        val delTrainingUC = mock<DeleteTrainingUC>()
        val getTrainingsUC = mock<GetTrainingsUC>()
        val selectTrainingUC = mock<SelectTrainingUC>()
        val updateSpeechUC = mock<UpdateSpeechUC>()

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            Dispatchers.setMain(StandardTestDispatcher())  //UnconfinedTestDispatcher  StandardTestDispatcher
//            viewModel = TrainingsViewModel(copyTrainingUC, delTrainingUC,getTrainingsUC,selectTrainingUC, updateSpeechUC)
        }
        @JvmStatic
        @AfterAll
        fun afterAll() { Dispatchers.resetMain() }
    }

    @Test
    @Order(1)
    fun testGetTrainingsSubmitEventToScreenStateRepository() = runTest {
//        listTraining.remove(training1)
//        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
//        whenever(repo.gets()).thenReturn(flowOf(listTraining))
//        viewModel.submitEvent(TrainingsEvent.Gets)
//        var actual = viewModel.screenState.value
//        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
//        Assertions.assertEquals(exceptionScreenState, actual)
    }
}