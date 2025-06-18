package com.count_out.domain.usecase

import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.trainings.TrainingRepo
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetTrainingsCaseTest {

    private val trainingRepo = mock<TrainingRepo>()
    private val useCase = GetTrainingsUC(mock(), trainingRepo)
    private val training1 = mock<Training>()
    private val training2 = mock<Training>()
    private val listTraining = listOf(training1,training2)

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runTest {
        val request = GetTrainingsUC.Request
        whenever(trainingRepo.gets()).thenReturn(flowOf(listTraining))
        val response = useCase.implementation_old(request).first()
        Assert.assertEquals(GetTrainingsUC.Response(listTraining), response)
    }
}