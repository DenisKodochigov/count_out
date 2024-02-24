package com.example.count_out.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.delay
import java.util.Locale


class SpeechManager(val context: Context) {

    private val show = false
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
                            speeching.value = true
//                            log(show, "SpeechManager.On Start")
                        }
                        override fun onDone(utteranceId: String) {
                            speeching.value = false
//                            log(show, "SpeechManager.On Done")
                        }
                        @Deprecated("Deprecated in Java",
                            ReplaceWith("Log.i(\"TextToSpeech\", \"On Error\")", "android.util.Log")
                        )
                        override fun onError(utteranceId: String) {
//                            log(show, "SpeechManager.On Error")
                        }
                    })
                }
            } else { tts = null }
        }
    }
    suspend fun speakOut(text: String, delay: Long = 0L){
        if (text.isNotEmpty()) {
            log(show, "SpeechManager.speakOut: $text")
            tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut")
            val d: Long = if (delay ==0L) text.length * 70L else delay
            delay(d)
        }
    }
    fun speakOutFlush(text: String){
        if (text.isNotEmpty()) {
            log(show, "SpeechManager.speakOut: $text")
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"speakOut")
        }
    }
    fun speakOut(text: String){
        if (text.isNotEmpty()) {
            log(show, "SpeechManager.speakOut: $text")
            tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut")
        }
    }
    fun onStop(){
        tts?.stop()
    }
    fun onDestroy(){
        tts?.shutdown()
    }
}