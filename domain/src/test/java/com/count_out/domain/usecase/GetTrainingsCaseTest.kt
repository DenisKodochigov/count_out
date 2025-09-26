package com.count_out.domain.usecase

import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.repository.plans.TrainingRepo
import com.count_out.domain.use_case.plans.GetTrainingsUC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock

class GetTrainingsCaseTest {

    private val trainingRepo = mock<TrainingRepo>()
    private val useCase = GetTrainingsUC(mock(), trainingRepo)
    private val training1 = mock<Plan>()
    private val training2 = mock<Plan>()
    private val listTraining = listOf(training1,training2)

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runTest {
        val request = GetTrainingsUC.Request
//        whenever(trainingRepo.gets()).thenReturn(flowOf(listTraining))
//        val response = useCase.implementation(request).first()
//        Assert.assertEquals(GetTrainingsUC.Response(listTraining), response)
    }
}