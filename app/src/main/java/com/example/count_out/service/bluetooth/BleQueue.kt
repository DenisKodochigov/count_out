package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import com.example.count_out.entity.Const.MAX_TRIES
import com.example.count_out.ui.view_components.lg
import java.util.concurrent.ConcurrentLinkedQueue

class BleQueue {
    private val commandQueue = ConcurrentLinkedQueue<()->Boolean>(emptyList())
    private var commandRunning = false
    private var nrTries = 0

    @SuppressLint("MissingPermission")
    fun addCommandInQueue(command: ()->Boolean)
    {
        if (commandQueue.add { command() }) { nextCommand() }
        else lg("ERROR: Could not enqueue read characteristic command")
    }

    private fun nextCommand() {
        if (commandRunning) { return }
        if (commandQueue.size > 0) {
            val command: (() -> Boolean) = commandQueue.peek()!!
            commandRunning = true
            nrTries = 0
            if(! command() ) retryCommand() else completedCommand()
        }
    }

    fun completedCommand() {
        commandRunning = false
        commandQueue.poll()
        nextCommand()
    }

    private fun retryCommand() {
        nrTries++
        commandRunning = false
        if (nrTries >= MAX_TRIES) {
            lg( "Max number of tries reached")
            commandQueue.poll()
        }
        nextCommand()
    }
}