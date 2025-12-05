package com.count_out.service.aii.batching

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BatchCollector<T>(
    private val batchSize: Int,
    private val maxDelayMs: Long,
    private val scope: CoroutineScope,
    private val onBatch: suspend (List<T>) -> Unit
) {
    private val buffer = ArrayList<T>()
    private val mutex = Mutex()
    private var lastFlushTime = System.currentTimeMillis()
    private var flushJob: Job? = null

    init {
        // Периодический флашер (гарантирует flush каждые maxDelayMs)
        flushJob = scope.launch {
            while (isActive) {
                delay(maxDelayMs)
                flushSafely()
            }
        }
    }
    suspend fun add(item: T) {
        var toFlush: List<T>? = null
        mutex.withLock {
            buffer.add(item)
            if (buffer.size >= batchSize) {
                toFlush = ArrayList(buffer)
                buffer.clear()
                lastFlushTime = System.currentTimeMillis()
            }
        }
        if (toFlush != null) scope.launch(Dispatchers.IO) { onBatch(toFlush) }
    }

    suspend fun flushSafely() {
        var toFlush: List<T>? = null
        mutex.withLock {
            if (buffer.isNotEmpty()) {
                toFlush = ArrayList(buffer)
                buffer.clear()
                lastFlushTime = System.currentTimeMillis()
            }
        }
        if (toFlush != null) scope.launch(Dispatchers.IO) { onBatch(toFlush) }
    }
    fun shutdown() { flushJob?.cancel() }
}