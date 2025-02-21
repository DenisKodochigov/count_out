package com.count_out.framework

import com.count_out.data.entity.SpeechImpl
import com.count_out.data.source.network.WeatherSource
import com.count_out.data.source.room.SpeechSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SpeechSourceTest {
    private val weatherSource = mock<WeatherSource>()
    private val speechSource = mock<SpeechSource>()

    @Test
    fun addSpeech(){
        val speech = expectedSpeech
        speechSource.add(speech)
        val captor = argumentCaptor<SpeechImpl>()
        verify(speechSource).add(captor.capture())
        Assert.assertEquals(speech.message, captor.firstValue.message)
    }
    @Test
    fun getSpeech() = runTest{
        whenever(speechSource.get(1)).thenReturn( flowOf(expectedSpeech) )
        val speech = speechSource.get(1).last()
        Assert.assertEquals(speech, expectedSpeech)
    }

    @Test
    fun delSpeech(){
        speechSource.del(1)
        verify(speechSource).del(any())
    }

    companion object {
        val expectedSpeech = SpeechImpl(idSpeech = 0, message = "test add speech",0,"")
//        fun buildSpeech() = SpeechImpl(idSpeech = 0, message = "test add speech",0,"")
    }
}