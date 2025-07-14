package com.count_out.presentation.example.list.prime

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.use_case.UseCase
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeConvertor
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock


class PrimeViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher ()
    private lateinit var viewModel: PrimeViewModel<String, PrimeConvertor<UseCase.Response, String>>
    private lateinit var converter:  PrimeConvertor<UseCase.Response, String>
    data class ResponseTest(val test: String): UseCase.Response

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)

        converter = object : PrimeConvertor<UseCase.Response, String>() {
            override fun makeSuccess(
                resultData: UseCase.Response, state: MutableStateFlow<String>, ):String = (resultData as ResponseTest).test }
        viewModel = object : PrimeViewModel<String, PrimeConvertor<UseCase.Response, String>>() {
            override fun initScreenState(): ScreenState<String> = ScreenState.Loading
            override fun initDataState(): String  = "init"
            override fun convertor(): PrimeConvertor<UseCase.Response, String> = converter
            override fun routeEvent(event: Event) {}
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitEvent() = runTest {
        val event = mock<Event>()
        val converter = object : PrimeConvertor<UseCase.Response, String>() {
            override fun makeSuccess(
                new: UseCase.Response, state: MutableStateFlow<String>, ): String = "$new" }

        viewModel = object : PrimeViewModel<String, PrimeConvertor<UseCase.Response, String>>()  {
            override fun initScreenState(): ScreenState<String> = ScreenState.Loading
            override fun initDataState(): String  = "init"
            override fun convertor(): PrimeConvertor<UseCase.Response, String> = converter
            override fun routeEvent(action: Event) { assertEquals(event, action) }
        }
        viewModel.submitEvent(event)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitState() = runTest {
        val response = ResponseTest(test = "test11111111")
        val expected = ScreenState.Success(response.test)
        val resultUC = ResultUC.Success(response)
        viewModel.submitState(resultUC)
        val result =  viewModel.screenState.value
//        advanceUntilIdle()
        assertEquals(expected, result)
    }
}