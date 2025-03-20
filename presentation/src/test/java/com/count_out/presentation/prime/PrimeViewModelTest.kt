package com.count_out.presentation.prime

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.use_case.UseCase
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeConvertor
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock


class PrimeViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: PrimeViewModel<String, PrimeConvertor<UseCase.Response, String>>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        val converter = object : PrimeConvertor<UseCase.Response, String>() {
            override fun convertSuccess(
                new: UseCase.Response, state: MutableStateFlow<String>, ): String = "result${new}" }
        viewModel = object : PrimeViewModel<String, PrimeConvertor<UseCase.Response, String>>() {
            override fun initScreenState(): ScreenState<String> = ScreenState.Loading
            override fun initDataState(): String  = "init"
            override fun initConvertor(): PrimeConvertor<UseCase.Response, String> = converter
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
    fun testSubmitAction() = runTest {
        val event = mock<Event>()
        val converter = object : PrimeConvertor<UseCase.Response, String>() {
            override fun convertSuccess(
                new: UseCase.Response, state: MutableStateFlow<String>, ): String = "result${new}" }

        viewModel = object : PrimeViewModel<String, PrimeConvertor<UseCase.Response, String>>()  {
            override fun initScreenState(): ScreenState<String> = ScreenState.Loading
            override fun initDataState(): String  = "init"
            override fun initConvertor(): PrimeConvertor<UseCase.Response, String> = converter
            override fun routeEvent(action: Event) {
               assertEquals(event, action) }
        }
        viewModel.submitEvent(event)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSubmitState() = runTest {
        val event = mock<Event>()
        val ress = mock<UseCase.Response>()
        data class Response(val test: String) : UseCase.Response
        val converter = object : PrimeConvertor<UseCase.Response, String>() {
            override fun convertSuccess(
                new: UseCase.Response, state: MutableStateFlow<String>, ): String = "test" }

        viewModel = object : PrimeViewModel<String, PrimeConvertor<UseCase.Response, String>>()  {
            override fun initScreenState(): ScreenState<String> = ScreenState.Loading
            override fun initDataState(): String  = "init"
            override fun initConvertor(): PrimeConvertor<UseCase.Response, String> = converter
            override fun routeEvent(action: Event) { assertEquals(event, action) }
        }
        val response = Response(test = "test")
        val resultUC = ResultUC.Success(response)
        viewModel.submitState(resultUC)
//        assertEquals(resultUC, converter.convertSuccess())//


        assertEquals(ScreenState.Success("test"), viewModel.screenState.value)
    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun testSubmitSingleEvent() = runBlockingTest {
//        val uiSingleEvent = mock<UiSingleEvent>()
//        viewModel.submitSingleEvent(uiSingleEvent)
//        assertEquals(uiSingleEvent, viewModel.singleEventFlow.first())
//    }
}