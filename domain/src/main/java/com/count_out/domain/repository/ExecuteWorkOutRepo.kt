package com.count_out.domain.repository

interface ExecuteWorkOutRepo {
    fun start()
    fun stop()
    fun pause()
    fun save()
    fun upInterval()
    fun downInterval()
}