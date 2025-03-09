package com.count_out.app.presentation.models

import android.content.Context
import com.count_out.app.presentation.view_components.lg
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class MessageApp @Inject constructor(@ApplicationContext val context: Context) {

    fun errorApi (errorMessage:String){
        lg("Error: $errorMessage")
    }
    fun errorApi (id: Int){
        lg("Error: ${context.getString(id)}")
    }
    fun errorApi (id: Int, errorMessage:String){
        lg("Error: ${context.getString(id)} $errorMessage")
    }
    fun messageApi (message:String){
        lg("Mess: $message")
    }
    fun messageApi (id: Int){
        lg("Mess: ${context.getString(id)}")
    }
    fun messageApi (id: Int, message:String){
        lg("Mess: ${context.getString(id)} $message")
    }
}