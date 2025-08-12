package com.count_out.framework

import android.R.id.message
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.room.SpeechSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SpeechSourceTest {
    private val speechSource = mock<SpeechSource>()
    val speech = TypeSource.SpeechT(
        SpeechImplD(idSpeech = 1, message = "message 1", duration = 1L, addMessage = " add message 1"))

    @Test
    fun addSpeech(){
        val speech = inputData
        speechSource.copy(speech)
        val captor = argumentCaptor<TypeSource.SpeechT>()
        verify(speechSource).copy(captor.capture())
        Assertions.assertEquals(speech.item.message, captor.firstValue.item.message)
    }
    @Test
    fun getSpeech() = runTest{
        val expected = ResultSource.Success(data = inputData)
        whenever(speechSource.get(speech)).thenReturn( flowOf(expected) )
        val speech = speechSource.get(speech).last()
        Assertions.assertEquals(expected, speech)
    }

    @Test
    fun delSpeech(){
        speechSource.del(speech)
        verify(speechSource).del(any())
    }

    companion object {
        val inputData =
            TypeSource.SpeechT(
                SpeechImplD(idSpeech = 0, message = "test add speech",0,""))
//        fun buildSpeech() = SpeechImpl(idSpeech = 0, message = "test add speech",0,"")
    }
}