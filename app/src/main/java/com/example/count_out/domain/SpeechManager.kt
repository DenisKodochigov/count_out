package com.example.count_out.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import com.example.count_out.ui.view_components.log
import java.util.Locale

class SpeechManager(val context: Context) {

    private val language = Locale.getDefault()
    private var tts:TextToSpeech? = null

    fun init(){
        tts = TextToSpeech(context){ status ->
            if ( status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage( language )
                if ( result  == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
//                    log(true, "SpeechManager Language: ${language.language} is not supported")
                    tts = null
                }
            } else {
//                log(true, "SpeechManager Status: $status")
                tts = null
            }
        }
    }
    fun speakOut(text: String){
        log(true, "SpeechManager.speakOut: $text")
        tts?.speak(text, TextToSpeech.QUEUE_ADD, null,"speakOut")
    }
    fun onStop(){
        tts?.stop()
    }
    fun onDestroy(){
        tts?.shutdown()
    }
}