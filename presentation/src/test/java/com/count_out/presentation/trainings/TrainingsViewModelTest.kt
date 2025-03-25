package com.count_out.presentation.trainings

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.ScreenState
import com.count_out.presentation.screens.trainings.TrainingsEvent
import com.count_out.presentation.screens.trainings.TrainingsState
import com.count_out.presentation.screens.trainings.TrainingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TrainingsViewModelTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()
    private val copyTrainingUC = mock<CopyTrainingUC>()
    private val delTrainingUC = mock<DeleteTrainingUC>()
    private val getTrainingsUC = mock<GetTrainingsUC>()
    private val selectTrainingUC = mock<SelectTrainingUC>()
    private lateinit var viewModel: TrainingsViewModel

    private val training1 = TrainingImplP(idTraining = 1)
    private val training2 = TrainingImplP(idTraining = 2)
    private val listTraining = mutableListOf(training1,training2)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = TrainingsViewModel(copyTrainingUC, delTrainingUC,getTrainingsUC,selectTrainingUC)
    }
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() { Dispatchers.resetMain() }

    @ExperimentalCoroutinesApi
    @Test
    fun testCopyTrainingsSubmitEventToScreenState() = runTest {
        whenever(copyTrainingUC.execute(CopyTrainingUC.Request(training1)))
            .thenReturn(flowOf(ResultUC.Success( CopyTrainingUC.Response(listTraining))))
        viewModel.submitEvent(TrainingsEvent.Copy(training1))
        listTraining.add(training1)
        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
        Thread.sleep(3000)
        assertEquals(exceptionScreenState, viewModel.screenState.value)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testDelTrainingsSubmitEventToScreenState() = runTest {
        listTraining.remove(training1)
        whenever(delTrainingUC.execute(DeleteTrainingUC.Request(training1)))
            .thenReturn(flowOf(ResultUC.Success( DeleteTrainingUC.Response(listTraining))))
        viewModel.submitEvent(TrainingsEvent.Del(training1))
        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
        Thread.sleep(3000)
        assertEquals(exceptionScreenState, viewModel.screenState.value)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testGetTrainingsSubmitEventToScreenState() = runTest {
        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
        whenever(getTrainingsUC.execute(GetTrainingsUC.Request))
            .thenReturn(flowOf(ResultUC.Success( GetTrainingsUC.Response(listTraining))))
        viewModel.submitEvent(TrainingsEvent.Gets)
        Thread.sleep(1000)
        assertEquals(exceptionScreenState, viewModel.screenState.value)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun testSelectedTrainingsSubmitEventToScreenState() = runTest {
        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState( selectedId = 1))
        whenever(selectTrainingUC.execute(SelectTrainingUC.Request(training1)))
            .thenReturn(flowOf(ResultUC.Success( SelectTrainingUC.Response(1))))
        viewModel.submitEvent(TrainingsEvent.Select(training1))
        Thread.sleep(1000)
        assertEquals(exceptionScreenState, viewModel.screenState.value)
    }
}