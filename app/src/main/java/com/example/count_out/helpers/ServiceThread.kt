package com.example.count_out.helpers

import android.os.Handler
import android.os.HandlerThread

class ServiceThread: Runnable {
    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler
    val handlerThreadName = "workout_thread"

    fun init(){
        handlerThread = HandlerThread(handlerThreadName)
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }
    fun destroy(){
        handler.removeCallbacksAndMessages(null)
        handler.looper.quit()
        handlerThread.quit()
    }
    fun run( task:()->Unit) {
        handler.post {
            task()
        }
    }
    override fun run( ) {
        handler.post {}
    }
}