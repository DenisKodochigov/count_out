package com.count_out.presentation.trainings

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.ScreenState
import com.count_out.presentation.screens.trainings.TrainingsEvent
import com.count_out.presentation.screens.trainings.TrainingsState
import com.count_out.presentation.screens.trainings.TrainingsViewModel
import com.count_out.presentation.trainings.TrainingsViewModelToUCTest.Companion.copyTrainingUC
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
class TrainingsViewModelToRepositoryTest {

    private val training1 = TrainingImplP(idTraining = 1)
    private val training2 = TrainingImplP(idTraining = 2)
    val listTraining = mutableListOf(training1,training2)

    companion object{
        lateinit var viewModel: TrainingsViewModel
        val repo = mock<TrainingRepo>()
        val delTrainingUC = mock<DeleteTrainingUC>()
        val getTrainingsUC = mock<GetTrainingsUC>()
        val selectTrainingUC = mock<SelectTrainingUC>()
        val updateSpeechUC = mock<UpdateSpeechUC>()

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            Dispatchers.setMain(StandardTestDispatcher())  //UnconfinedTestDispatcher  StandardTestDispatcher
            viewModel = TrainingsViewModel(copyTrainingUC, delTrainingUC,getTrainingsUC,selectTrainingUC, updateSpeechUC)
        }
        @JvmStatic
        @AfterAll
        fun afterAll() { Dispatchers.resetMain() }
    }

    @Test
    @Order(1)
    fun testGetTrainingsSubmitEventToScreenStateRepository() = runTest {
//        listTraining.remove(training1)
        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
        whenever(repo.gets()).thenReturn(flowOf(listTraining))
        viewModel.submitEvent(TrainingsEvent.Gets)
        var actual = viewModel.screenState.value
        viewModel.screenState.filter{it != ScreenState.Loading}.take(1).collect { actual = it }
        Assertions.assertEquals(exceptionScreenState, actual)
    }
}