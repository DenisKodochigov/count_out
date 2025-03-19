package com.count_out.presentation.state

import com.count_out.domain.entity.throwable.ThrowableUC.TrainingThrow
import com.count_out.presentation.screens.prime.PrimeConvertor
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.presentation.screens.prime.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PrimeConverterTest {
    private val converter = object : PrimeConvertor<String, String>() {
        override fun convertSuccess(data: String, state: MutableStateFlow<String>): String ="result${data}"
    }

    @Test
    fun testConvertError() {
        val errorMessage = "errorMessage"
        val exception = mock<TrainingThrow>()
        whenever(exception.localizedMessage).thenReturn(errorMessage)
        val errorResult = ResultUC.Error(exception)
        val result = converter.convert(errorResult)
        assertEquals(ScreenState.Error(errorMessage), result)
    }

    @Test
    fun testConvertSuccess() {
        val data = "data"
        val successResult = ResultUC.Success(data)
        val result = converter.convert(successResult)
        assertEquals(ScreenState.Success("result${data}"), result)
    }
}