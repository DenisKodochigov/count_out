package com.example.count_out.not_used

//import android.annotation.SuppressLint
//import com.example.count_out.entity.Const.MAX_TRIES
//import com.example.count_out.ui.view_components.lg
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.runBlocking
//import java.util.concurrent.ConcurrentLinkedQueue
//
//class BleQueue {
//    private val commandQueue = ConcurrentLinkedQueue<()->Boolean>(emptyList())
//    private var commandRunning = false
//    private var nrTries = 0
//
//    @SuppressLint("MissingPermission")
//    fun addCommandInQueue(command: ()->Boolean)
//    {
//        if (commandQueue.add { command() }) { nextCommand() }
//        else lg("ERROR: Could not enqueue read characteristic command")
//    }
//
//    private fun nextCommand() {
//        if (commandRunning) { return }
//        if (commandQueue.size > 0) {
//            val command: (() -> Boolean) = commandQueue.peek()!!
//            commandRunning = true
//            if(! command() ) retryCommand() else completedCommand()
//        }
//    }
//
//    fun completedCommand() {
//        commandRunning = false
//        nrTries = 0
//        commandQueue.poll()
//        nextCommand()
//    }
//
//    private fun retryCommand() {
//        nrTries++
//        runBlocking { delay(1000L) }
//        commandRunning = false
//        if (nrTries >= MAX_TRIES) {
//            nrTries = 0
//            commandQueue.poll()
//        }
//        nextCommand()
//    }
//}