package com.count_out.presentation.trainings

import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.plans.CopyTrainingUC
import com.count_out.domain.use_case.plans.DeleteTrainingUC
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.SelectTrainingUC
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.plans.PlansConvertor
import com.count_out.presentation.screens.plans.PlansState
import com.count_out.presentation.screens.prime.Event
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TrainingsConverterTest {
    private val converter = PlansConvertor()
    private val trainingState = MutableStateFlow(PlansState(
        trainings = emptyList(),
        selectedId =0,
        event = { fun run(ev: Event) {} }
    ))
    private val training1 = TrainingImplP(idTraining = 1)
    private val training2 = TrainingImplP(idTraining = 2)
    private val listTraining = listOf(training1,training2)
    private val typeListTraining = TypeRepo.PlansT(listOf(training1,training2))

    @Test
    fun testGetTrainingsConvert() {
        val response = GetTrainingsUC.Response(trainings = typeListTraining)
        val result = converter.makeSuccess(response, trainingState)
        val exception = trainingState.value.copy(trainings = listTraining)
        Assertions.assertEquals(exception, result)
    }
    @Test
    fun testCopyTrainingsConvert() {
        val listTraining = listOf(TrainingImplP())
        val response = CopyTrainingUC.Response(trainings = typeListTraining)
        val result = converter.makeSuccess(response, trainingState)
        val exception = trainingState.value.copy(trainings = listTraining)
        Assertions.assertEquals(exception, result)
    }
    @Test
    fun testDeleteTrainingsConvert() {
        val listTraining = listOf(TrainingImplP())
        val response = DeleteTrainingUC.Response(trainings = typeListTraining)
        val result = converter.makeSuccess(response, trainingState)
        val exception = trainingState.value.copy(trainings = listTraining)
        Assertions.assertEquals(exception, result)
    }
//    @Test
//    fun testUpdateTrainingsConvert() {
//        val listTraining = listOf(TrainingImplP())
//        val response = UpdateTrainingUC.Response(trainings = training1)
//        val result = converter.convertSuccess(response, trainingState)
//        val exception = trainingState.value.copy(trainings = listTraining)
//        Assertions.assertEquals(exception, result)
//    }
    @Test
    fun testSelectTrainingsConvert() {
        val response = SelectTrainingUC.Response(selectedTraining = TypeRepo.IntT(1))
        val result = converter.makeSuccess(response, trainingState)
        val exception = trainingState.value.copy(selectedId = 1)
        Assertions.assertEquals(exception, result)
    }
}