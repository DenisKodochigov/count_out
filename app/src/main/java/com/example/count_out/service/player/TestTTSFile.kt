package com.example.count_out.service.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.count_out.domain.SpeechManager
import com.example.count_out.ui.view_components.lg
import java.io.File
import javax.inject.Inject




//    fun writeFile(filename: String, fileContents: String){
//        try {
//            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
//                it.write(fileContents.toByteArray())}
//        }catch(e: Exception){
//            lg("error: $e")
//            e.printStackTrace()
//        }
//    }