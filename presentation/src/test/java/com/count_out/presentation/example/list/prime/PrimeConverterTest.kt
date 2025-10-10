package com.count_out.presentation.example.list.prime

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import com.count_out.presentation.screens.prime.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PrimeConverterTest {
    private val converter = object : PrimeConvertor<String, String>() {
        override fun makeSuccess(resultData: String, state: MutableStateFlow<String>): String ="result${resultData}"
    }

    @Test
    fun testConvertError() {
        val errorMessage = "errorMessage"
        val state = MutableStateFlow("state")
        val exception = mock<ThrowableUC.TrainingThrow>()
        whenever(exception.localizedMessage).thenReturn(errorMessage)
        val errorResult = ResultDomain.Error(exception)
        val result = converter.make(errorResult, state)
        Assert.assertEquals(ScreenState.Error(errorMessage), result)
    }

    @Test
    fun testConvertSuccess() {
        val data = "data"
        val state = MutableStateFlow("state")
        val successResult = ResultDomain.Success(data)
        val result = converter.make(successResult, state)
        Assert.assertEquals(ScreenState.Success("result${data}"), result)
    }
}