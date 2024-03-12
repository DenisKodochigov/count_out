package com.example.count_out.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.Const.durationChar
import com.example.count_out.entity.Const.intervalDelay
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import javax.inject.Singleton

@Singleton
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
                            log(show, "SpeechManager.On Start $tts")
                        }
                        override fun onDone(utteranceId: String) {
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
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ){
        if (text.length > 1) {
            flowStateServiceMutable.emit ( StateWorkOut(state = text))
            speakOutAdd(text)
            delayMy(delay = text.length * durationChar, pause)
        }
    }
    private fun speakOutAdd(text: String){
//        log(show, "SpeechManager.speakOut: $text")
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut")
    }

    fun speakOutFlush(text: String){
//        log(show, "SpeechManager.speakOut: $text")
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"speakOut")
    }

    private suspend fun delayMy(delay: Long, pause: MutableState<Boolean>){
        var count = 0
        val delta = (delay/intervalDelay).toInt()
        while (count <= delta){
            if (!pause.value) count++
            delay(intervalDelay)
        }
    }
}