package com.example.count_out.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.entity.Speech
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.VariablesOutService
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import javax.inject.Singleton


@Singleton
class SpeechManager(val context: Context) {

    private var durationStart: Long = 0L
    private var durationEnd: Long = 0L
    private var duration: Long = 0L
    private var tts:TextToSpeech? = null
    private var idSpeech: Int = 0
    private val speeching: MutableState<Boolean> = mutableStateOf(true)

    fun init(){
        tts = TextToSpeech(context){ status ->
            if ( status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage( Locale.getDefault() )
                if ( result  == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    tts = null
                } else {
                    tts?.setOnUtteranceProgressListener(
                        object : UtteranceProgressListener(){
                            override fun onStart(utteranceId: String) {
                                durationStart = System.currentTimeMillis()
                                speeching.value = true
                            }
                            override fun onDone(utteranceId: String) {
                                durationEnd = System.currentTimeMillis()
                                duration = durationEnd - durationStart
                                speeching.value = false
                            }
                            @Deprecated("Deprecated in Java",
                                ReplaceWith("Log.i(\"TextToSpeech\", \"On Error\")", "android.util.Log")
                            )
                            override fun onError(utteranceId: String) {
                                lg("SpeechManager.On Error $utteranceId")
                            }
                        }
                    )
                }
            } else { tts = null }
        }
    }
    suspend fun speech(variablesOut: VariablesOutService, speech: Speech): Long
    {
        if (variablesOut.stateRunning.value != StateRunning.Stopped){
            val speechText = speech.message + " " + speech.addMessage
            if ((speechText).length > 1) {
                lg("speak begin $speechText")
                variablesOut.addMessage(speechText)
                speakOutAdd(speechText, variablesOut.stateRunning)
                lg("begin isSpeaking ${tts?.isSpeaking}; speeching: ${speeching.value}  ")
                while (speeching.value || variablesOut.stateRunning.value == StateRunning.Pause || tts?.isSpeaking == true) {
                    delay(100L)
                }
                lg("end isSpeaking ${tts?.isSpeaking}; speeching: ${speeching.value}  ")
                if( speech.duration == 0L && speech.idSpeech > 0 && duration > 0 ){
                    variablesOut.durationSpeech.value = speech.idSpeech to duration
                }
                lg("speak end $speechText")
            }
        } else {
            stopTts()
        }
        return durationEnd
    }
    private fun speakOutAdd(text: String, speakEnabled: MutableStateFlow<StateRunning>){
        if (speakEnabled.value != StateRunning.Stopped){
            speeching.value = true
            tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut$idSpeech")
        }
    }
    fun speakOutFlush(text: String, speakEnabled: MutableStateFlow<StateRunning>){
        if (speakEnabled.value != StateRunning.Stopped){
            speeching.value = true
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"speakOut$idSpeech")
        }
    }
    fun getDuration() = durationEnd

    private fun stopTts(){
        tts!!.stop()
        tts!!.shutdown()
        tts = null
    }
}
