package com.count_out.domain

//class DispatcherTestRule : TestRule {
//    @ExperimentalCoroutinesApi
//    val testDispatcher = StandardTestDispatcher()
//    @ExperimentalCoroutinesApi
//    override fun apply(base: Statement?, description: Description?): Statement {
//        try {
//            Dispatchers.setMain(testDispatcher)
//            base?.evaluate()
//        } catch (e: Exception) {
//        } finally {
//            Dispatchers.resetMain()
//            testDispatcher.cleanupTestCoroutines()
//        }
//        return base!!
//    }
//}