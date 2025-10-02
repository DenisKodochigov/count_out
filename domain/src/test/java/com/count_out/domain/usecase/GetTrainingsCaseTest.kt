package com.count_out.domain.usecase

import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.repository.plans.PlanRepo
import com.count_out.domain.use_case.plans.GetPlansUC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock

class GetTrainingsCaseTest {

    private val planRepo = mock<PlanRepo>()
    private val useCase = GetPlansUC(mock(), planRepo)
    private val training1 = mock<Plan>()
    private val training2 = mock<Plan>()
    private val listTraining = listOf(training1,training2)

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runTest {
        val request = GetPlansUC.Request
//        whenever(trainingRepo.gets()).thenReturn(flowOf(listTraining))
//        val response = useCase.implementation(request).first()
//        Assert.assertEquals(GetTrainingsUC.Response(listTraining), response)
    }
}