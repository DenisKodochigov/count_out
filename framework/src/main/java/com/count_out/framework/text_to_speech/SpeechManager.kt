package com.count_out.framework.text_to_speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.count_out.data.models.RunningState
import com.count_out.data.router.models.DataFromWork
import com.count_out.domain.entity.Speech
import kotlinx.coroutines.delay
import java.util.Locale
import javax.inject.Singleton

@Singleton
class SpeechManager(val context: Context) {

//    private val messengerA = MessageApp(context)
    private var durationStart: Long = 0L
    private var durationEnd: Long = 0L
    private var duration: Long = 0L
    private var tts:TextToSpeech? = null
    private var idSpeech: Int = 0

    fun init(callBack: ()-> Unit){
        tts = TextToSpeech(context){ status ->
//            messengerA.messageApi("TextToSpeech(context).status $status")
            if ( status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage( Locale.getDefault() )
                if ( result  == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
//                    messengerA.errorApi("TextToSpeech not support locale language.")
                    tts = null
                } else {
//                    messengerA.messageApi("TextToSpeech.setOnUtteranceProgressListener")
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
//                                messengerA.errorApi( R.string.speech_manager_error, " $utteranceId")}
                            }
                        }
                    )
                    callBack()
                }
            } else {
//                messengerA.messageApi("TextToSpeech not created")
                tts = null }
        }
    }

    suspend fun speech(dataFromWork: DataFromWork, speech: Speech): Long {
        dataFromWork.trap()
        val speechText = speech.message + " " + speech.addMessage
        if ((speechText).length > 1) {
            speakOutAdd(speechText, dataFromWork)
            while (tts?.isSpeaking == true || dataFromWork.runningState.value == RunningState.Paused)
            { delay(500L) }
            if( speech.duration == 0L && speech.idSpeech > 0 && duration > 0 ){
                dataFromWork.durationSpeech.value = speech.idSpeech to duration
            }
        }
        return durationEnd
    }
    private fun speakOutAdd(text: String, dataFromWork: DataFromWork){
        dataFromWork.trap()
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut$idSpeech")
    }
    fun speakOutFlush(text: String, dataFromWork: DataFromWork){
        dataFromWork.trap()
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"speakOut$idSpeech")
    }
    fun speakOutFlushBusy(text: String, dataFromWork: DataFromWork){
        dataFromWork.trap()
        if (tts?.isSpeaking == false) tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"speakOut$idSpeech")
    }
    fun getSpeeching() = tts?.isSpeaking ?: false
    fun stopTts(){
        tts?.let {
            it.stop()
            it.shutdown()
        }
        tts = null
    }
}
