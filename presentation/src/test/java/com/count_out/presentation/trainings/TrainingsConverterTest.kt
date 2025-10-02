//package com.count_out.presentation.trainings
//
//import com.count_out.domain.entity.TypeRepo
//import com.count_out.domain.use_case.plans.CopyPlanUC
//import com.count_out.domain.use_case.plans.DeletePlanUC
//import com.count_out.domain.use_case.plans.GetPlansUC
//import com.count_out.domain.use_case.plans.SelectPlanUC
//import com.count_out.presentation.models.TrainingImplP
//import com.count_out.presentation.screens.plans.PlansConvertor
//import com.count_out.presentation.screens.plans.PlansState
//import com.count_out.presentation.screens.prime.Event
//import kotlinx.coroutines.flow.MutableStateFlow
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//
//class TrainingsConverterTest {
//    private val converter = PlansConvertor()
//    private val trainingState = MutableStateFlow(PlansState(
//        trainings = emptyList(),
//        selectedId =0,
//        event = { fun run(ev: Event) {} }
//    ))
//    private val training1 = TrainingImplP(idTraining = 1)
//    private val training2 = TrainingImplP(idTraining = 2)
//    private val listTraining = listOf(training1,training2)
//    private val typeListTraining = TypeRepo.PlansT(listOf(training1,training2))
//
//    @Test
//    fun testGetTrainingsConvert() {
//        val response = GetPlansUC.Response(trainings = typeListTraining)
//        val result = converter.makeSuccess(response, trainingState)
//        val exception = trainingState.value.copy(trainings = listTraining)
//        Assertions.assertEquals(exception, result)
//    }
//    @Test
//    fun testCopyTrainingsConvert() {
//        val listTraining = listOf(TrainingImplP())
//        val response = CopyPlanUC.Response(trainings = typeListTraining)
//        val result = converter.makeSuccess(response, trainingState)
//        val exception = trainingState.value.copy(trainings = listTraining)
//        Assertions.assertEquals(exception, result)
//    }
//    @Test
//    fun testDeleteTrainingsConvert() {
//        val listTraining = listOf(TrainingImplP())
//        val response = DeletePlanUC.Response(trainings = typeListTraining)
//        val result = converter.makeSuccess(response, trainingState)
//        val exception = trainingState.value.copy(trainings = listTraining)
//        Assertions.assertEquals(exception, result)
//    }
////    @Test
////    fun testUpdateTrainingsConvert() {
////        val listTraining = listOf(TrainingImplP())
////        val response = UpdateTrainingUC.Response(trainings = training1)
////        val result = converter.convertSuccess(response, trainingState)
////        val exception = trainingState.value.copy(trainings = listTraining)
////        Assertions.assertEquals(exception, result)
////    }
//    @Test
//    fun testSelectTrainingsConvert() {
//        val response = SelectPlanUC.Response(selectedTraining = TypeRepo.IntT(1))
//        val result = converter.makeSuccess(response, trainingState)
//        val exception = trainingState.value.copy(selectedId = 1)
//        Assertions.assertEquals(exception, result)
//    }
//}