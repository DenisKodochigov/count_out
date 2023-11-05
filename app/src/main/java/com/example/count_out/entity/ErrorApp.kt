package com.example.count_out.entity

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.count_out.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorApp @Inject constructor(@ApplicationContext val context: Context) {

    fun errorApi (errorMessage:String){

        val toastMessage = when(errorMessage){
            "error_addProduct" -> context.getString(R.string.error_1)
            else -> context.getString(R.string.error_2)
        }
        if (toastMessage.isNotEmpty()) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

