package com.example.count_out.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.Const.durationChar
import com.example.count_out.entity.VariablesOutService
import com.example.count_out.helpers.delayMy
import com.example.count_out.ui.view_components.log
import java.util.Locale
import javax.inject.Singleton

@Singleton
class SpeechManager(val context: Context) {

    private val show = false
    private var duration: Long = 0L
    private var tts:TextToSpeech? = null
    val speeching: MutableState<Boolean> = mutableStateOf(true)

    fun init(){
        log(show, "SpeechManager.init")
        tts = TextToSpeech(context){ status ->
            if ( status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage( Locale.getDefault() )
                if ( result  == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    tts = null
                } else {
                    tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener(){
                        override fun onStart(utteranceId: String) {
                            duration = System.currentTimeMillis()
                            speeching.value = true
                            log(show, "SpeechManager.On Start $tts")
                        }
                        override fun onDone(utteranceId: String) {
                            duration = System.currentTimeMillis() - duration
                            speeching.value = false
                            log(show, "SpeechManager.On Done $tts")
                        }
                        @Deprecated("Deprecated in Java",
                            ReplaceWith("Log.i(\"TextToSpeech\", \"On Error\")", "android.util.Log")
                        )
                        override fun onError(utteranceId: String) {
                            log(show, "SpeechManager.On Error $utteranceId")
                        }
                    })
                }
            } else { tts = null }
        }
    }
    suspend fun speech(
        text: String,
        variablesOut: VariablesOutService
    ): Long{
        duration = 0
        if (text.length > 1) {
            variablesOut.addMessage(text)
            speakOutAdd(text)
            delayMy(delay = text.length * durationChar, variablesOut.stateRunning)
        }
        return duration
    }
    private fun speakOutAdd(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut")
    }

    fun speakOutFlush(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"speakOut")
    }


    fun getDuration() = duration
}