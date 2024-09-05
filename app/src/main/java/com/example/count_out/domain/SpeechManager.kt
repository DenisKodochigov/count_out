package com.example.count_out.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.Speech
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import javax.inject.Singleton

@Singleton
class SpeechManager(val context: Context) {

    private val messengerA = MessageApp(context)
    private var durationStart: Long = 0L
    private var durationEnd: Long = 0L
    private var duration: Long = 0L
    private var tts:TextToSpeech? = null
    private var idSpeech: Int = 0

    fun init(callBack: ()-> Unit){
        tts = TextToSpeech(context){ status ->
            messengerA.messageApi("TextToSpeech(context).status $status")
            if ( status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage( Locale.getDefault() )
                if ( result  == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    messengerA.errorApi("TextToSpeech not support locale language.")
                    tts = null
                } else {
                    messengerA.messageApi("TextToSpeech.setOnUtteranceProgressListener")
                    tts?.setOnUtteranceProgressListener(
                        object : UtteranceProgressListener(){
                            override fun onStart(utteranceId: String) {
                                durationStart = System.currentTimeMillis()
//                                messengerA.messageApi("TextToSpeech onStart")
                            }
                            override fun onDone(utteranceId: String) {
                                durationEnd = System.currentTimeMillis()
                                duration = durationEnd - durationStart
//                                messengerA.messageApi("TextToSpeech onDone")
                            }
                            @Deprecated("Deprecated in Java", ReplaceWith("Log.i(\"TextToSpeech\", \"On Error\")", "android.util.Log"))
                            override fun onError(utteranceId: String) {
                                messengerA.errorApi("SpeechManager.On Error $utteranceId")}
                        }
                    )
                    callBack()
                }
            } else {
                messengerA.messageApi("TextToSpeech not created")
                tts = null }
        }
    }
    suspend fun speech(sendToUI: SendToUI, speech: Speech): Long {
        if (sendToUI.runningState.value != RunningState.Stopped){
            val speechText = speech.message + " " + speech.addMessage
            if ((speechText).length > 1) {
                sendToUI.addMessage(speechText)
                speakOutAdd(speechText, sendToUI.runningState)
                while (tts?.isSpeaking == true || sendToUI.runningState.value == RunningState.Paused)
                    { delay(500L) }
                if( speech.duration == 0L && speech.idSpeech > 0 && duration > 0 ){
                    sendToUI.durationSpeech.value = speech.idSpeech to duration
                }
            }
        }
        return durationEnd
    }
    private fun speakOutAdd(text: String, speakEnabled: MutableStateFlow<RunningState>){
//        messengerA.messageApi("SpeechManager.speakOutAdd $text")
        if (speakEnabled.value != RunningState.Stopped){
            tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut$idSpeech")
        }
    }
    fun speakOutFlush(text: String, speakEnabled: MutableStateFlow<RunningState>){
//        messengerA.messageApi("SpeechManager.speakOutFlush")
        if (speakEnabled.value != RunningState.Stopped){
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"speakOut$idSpeech")
        }
    }
    fun getSpeeching() = tts?.isSpeaking ?: false
    fun stopTts(){
        tts!!.stop()
        tts!!.shutdown()
        tts = null
    }
}
