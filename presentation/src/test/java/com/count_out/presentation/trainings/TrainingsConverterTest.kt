package com.count_out.presentation.trainings

import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import com.count_out.domain.use_case.trainings.UpdateTrainingUC
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.trainings.TrainingsConvertor
import com.count_out.presentation.screens.trainings.TrainingsState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TrainingsConverterTest {
    private val converter = TrainingsConvertor()
    private val trainingState = MutableStateFlow(TrainingsState())
    private val training1 = TrainingImplP(idTraining = 1)
    private val training2 = TrainingImplP(idTraining = 2)
    private val listTraining = listOf(training1,training2)

    @Test
    fun testGetTrainingsConvert() {
        val response = GetTrainingsUC.Response(trainings = listTraining)
        val result = converter.convertSuccess(response, trainingState)
        val exception = trainingState.value.copy(trainings = listTraining)
        Assertions.assertEquals(exception, result)
    }
    @Test
    fun testCopyTrainingsConvert() {
        val listTraining = listOf(TrainingImplP())
        val response = CopyTrainingUC.Response(trainings = listTraining)
        val result = converter.convertSuccess(response, trainingState)
        val exception = trainingState.value.copy(trainings = listTraining)
        Assertions.assertEquals(exception, result)
    }
    @Test
    fun testDeleteTrainingsConvert() {
        val listTraining = listOf(TrainingImplP())
        val response = DeleteTrainingUC.Response(trainings = listTraining)
        val result = converter.convertSuccess(response, trainingState)
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
        val response = SelectTrainingUC.Response(selectedTraining = 1)
        val result = converter.convertSuccess(response, trainingState)
        val exception = trainingState.value.copy(selectedId = 1)
        Assertions.assertEquals(exception, result)
    }
}