//package com.example.count_out.helpers
//
//import android.os.Handler
//import android.os.HandlerThread
//import com.example.count_out.ui.view_components.log
//
//class ServiceThread: Runnable {
//    private lateinit var handlerThread: HandlerThread
//    private lateinit var handler: Handler
//    private val handlerThreadName = "workout_thread"
//
//    fun init(){
//        handlerThread = HandlerThread(handlerThreadName)
//        handlerThread.start()
//        handler = Handler(handlerThread.looper)
//    }
//    fun destroy() {
//        handler.removeCallbacksAndMessages(null)
//        handler.looper.quit()
//        handlerThread.quit()
//    }
//    fun run( task:()->Unit) {
//        log(true, "run( task:()->Unit). handler = $handler")
//        handler.post {
//            task()
//        }
//    }
//    override fun run( ) {
//        log(true, "run(). handler = $handler")
//        handler.post {}
//    }
//}