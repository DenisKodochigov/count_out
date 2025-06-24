package com.count_out.data

import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TrainingsViewModelToRepositoryTest {
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    private val training1 = TrainingImplD(idTraining = 1)
//    private val training2 = TrainingImplD(idTraining = 2)
//    val listTraining = mutableListOf(training1,training2)
//
//    companion object{
//        val repo = mock<TrainingRepoImpl>()
//
//        @JvmStatic
//        @BeforeAll
//        fun beforeAll() { Dispatchers.setMain(StandardTestDispatcher()) }
//        @JvmStatic
//        @AfterAll
//        fun afterAll() { Dispatchers.resetMain() }
//    }
//
//    @Test
//    @Order(1)
//    fun testGetTrainingsSubmitEventToScreenStateRepository() = runTest {
////        listTraining.remove(training1)
//        val exceptionScreenState = ""
//        whenever(repo.gets()).thenReturn(flowOf(listTraining))
//        var actual = "viewModel.screenState.value"
//        Assertions.assertEquals(exceptionScreenState, actual)
//    }
}