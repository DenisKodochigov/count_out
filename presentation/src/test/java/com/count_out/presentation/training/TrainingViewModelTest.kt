//package com.count_out.presentation.training
//
//import com.count_out.domain.entity.throwable.ResultUC
//import com.count_out.domain.repository.trainings.TrainingRepo
//import com.count_out.domain.use_case.UseCase
//import com.count_out.domain.use_case.exercise.ChangeSequenceExerciseUC
//import com.count_out.domain.use_case.exercise.CopyExerciseUC
//import com.count_out.domain.use_case.exercise.DeleteExerciseUC
//import com.count_out.domain.use_case.exercise.UpdateExerciseUC
//import com.count_out.domain.use_case.other.CollapsingUC
//import com.count_out.domain.use_case.other.ShowBottomSheetUC
//import com.count_out.domain.use_case.set.CopySetUC
//import com.count_out.domain.use_case.set.DeleteSetUC
//import com.count_out.domain.use_case.set.UpdateSetUC
//import com.count_out.domain.use_case.speech.UpdateSpeechKitUC
//import com.count_out.domain.use_case.trainings.CopyTrainingUC
//import com.count_out.domain.use_case.trainings.DeleteTrainingUC
//import com.count_out.domain.use_case.trainings.GetTrainingUC
//import com.count_out.domain.use_case.trainings.GetTrainingsUC
//import com.count_out.domain.use_case.trainings.SelectTrainingUC
//import com.count_out.domain.use_case.trainings.UpdateTrainingUC
//import com.count_out.presentation.models.TrainingImplP
//import com.count_out.presentation.screens.prime.ScreenState
//import com.count_out.presentation.screens.training.TrainingViewModel
//import com.count_out.presentation.screens.trainings.TrainingsEvent
//import com.count_out.presentation.screens.trainings.TrainingsState
//import com.count_out.presentation.screens.trainings.TrainingsViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.whenever
//
//class TrainingViewModelTest {
//    @ExperimentalCoroutinesApi
//    private val testDispatcher = UnconfinedTestDispatcher()
//    private lateinit var getTrainingUC: GetTrainingUC
//    private lateinit var updateTrainingUC: UpdateTrainingUC
//    private lateinit var copyExerciseUC: CopyExerciseUC
//    private lateinit var delExerciseUC: DeleteExerciseUC
//    private lateinit var updateExerciseUC: UpdateExerciseUC
//    private lateinit var changeSequenceExerciseUC: ChangeSequenceExerciseUC
//    private lateinit var copySetUC: CopySetUC
//    private lateinit var deleteSetUC: DeleteSetUC
//    private lateinit var changeSetUC: UpdateSetUC
//    private lateinit var showBottomSheetUC: ShowBottomSheetUC
//    private lateinit var collapsingSetUC: CollapsingUC
//    private lateinit var updateSpeechKitUC: UpdateSpeechKitUC
//    private lateinit var viewModel: TrainingViewModel
//
//    private val training1 = TrainingImplP(idTraining = 1)
//    private val training2 = TrainingImplP(idTraining = 2)
//    private val listTraining = mutableListOf(training1,training2)
//
//    @ExperimentalCoroutinesApi
//    @Before
//    fun setUp() = runTest {
//        Dispatchers.setMain(testDispatcher)
//        viewModel = TrainingViewModel(getTrainingUC, updateTrainingUC,copyExerciseUC,delExerciseUC,
//            updateExerciseUC, changeSequenceExerciseUC, copySetUC, deleteSetUC, changeSetUC,
//            showBottomSheetUC, collapsingSetUC, updateSpeechKitUC)
//    }
//    @ExperimentalCoroutinesApi
//    @After
//    fun tearDown() { Dispatchers.resetMain() }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testCopyTrainingsSubmitEventToScreenState() = runTest {
//        whenever(copyTrainingUC.execute(CopyTrainingUC.Request(training1)))
//            .thenReturn(flowOf(ResultUC.Success( CopyTrainingUC.Response(listTraining))))
//        viewModel.submitEvent(TrainingsEvent.Copy(training1))
//        listTraining.add(training1)
//        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
//        Thread.sleep(3000)
//        assertEquals(exceptionScreenState, viewModel.screenState.value)
//    }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testDelTrainingsSubmitEventToScreenState() = runTest {
//        listTraining.remove(training1)
//        whenever(delTrainingUC.execute(DeleteTrainingUC.Request(training1)))
//            .thenReturn(flowOf(ResultUC.Success( DeleteTrainingUC.Response(listTraining))))
//        viewModel.submitEvent(TrainingsEvent.Del(training1))
//        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
//        Thread.sleep(3000)
//        assertEquals(exceptionScreenState, viewModel.screenState.value)
//    }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testGetTrainingsSubmitEventToScreenState() = runTest {
//        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState(trainings = listTraining))
//        whenever(getTrainingsUC.execute(GetTrainingsUC.Request))
//            .thenReturn(flowOf(ResultUC.Success( GetTrainingsUC.Response(listTraining))))
//        viewModel.submitEvent(TrainingsEvent.Gets)
//        Thread.sleep(1000)
//        assertEquals(exceptionScreenState, viewModel.screenState.value)
//    }
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testSelectedTrainingsSubmitEventToScreenState() = runTest {
//        val exceptionScreenState = ScreenState.Success(dataState = TrainingsState( selectedId = 1))
//        whenever(selectTrainingUC.execute(SelectTrainingUC.Request(training1)))
//            .thenReturn(flowOf(ResultUC.Success( SelectTrainingUC.Response(1))))
//        viewModel.submitEvent(TrainingsEvent.Select(training1))
//        Thread.sleep(1000)
//        assertEquals(exceptionScreenState, viewModel.screenState.value)
//    }
//}