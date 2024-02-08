package com.example.count_out.domain

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.Locale

class SpeechWorkOut(val context: Context) {

    private val language = Locale.getDefault()
    private var tts:TextToSpeech? = null

    fun init(){
        tts = TextToSpeech(context){ status ->
            if ( status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage( language )
                if ( result  == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(context, "Language: ${language.language} is not supported",Toast.LENGTH_SHORT).show()
                } else {
                    tts = null
                }
            } else {
                tts = null
            }
        }
    }
    fun speakOut(text: String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }
    fun onStop(){
        tts?.stop()
    }
    fun onDestroy(){
        tts?.shutdown()
    }
}