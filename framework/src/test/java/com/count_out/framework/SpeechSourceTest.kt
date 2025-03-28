package com.count_out.framework

import com.count_out.data.models.SpeechImplD
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

    @Test
    fun addSpeech(){
        val speech = expectedSpeech
        speechSource.copy(speech)
        val captor = argumentCaptor<SpeechImplD>()
        verify(speechSource).copy(captor.capture())
        Assertions.assertEquals(speech.message, captor.firstValue.message)
    }
    @Test
    fun getSpeech() = runTest{
        whenever(speechSource.get(1)).thenReturn( flowOf(expectedSpeech) )
        val speech = speechSource.get(1).last()
        Assertions.assertEquals(speech, expectedSpeech)
    }

    @Test
    fun delSpeech(){
        speechSource.del(1)
        verify(speechSource).del(any())
    }

    companion object {
        val expectedSpeech = SpeechImplD(idSpeech = 0, message = "test add speech",0,"")
//        fun buildSpeech() = SpeechImpl(idSpeech = 0, message = "test add speech",0,"")
    }
}