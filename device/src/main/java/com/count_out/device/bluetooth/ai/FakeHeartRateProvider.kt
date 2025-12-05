package com.count_out.device.bluetooth.ai

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FakeHeartRateProvider : HeartRateProvider {
    override val heartRateFlow: Flow<Int> = flow {
        while (true) {
            emit(60 + Random.nextInt(0, 80))
            delay(1000)
        }
    }

    override suspend fun connect() {}
    override suspend fun disconnect() {}
}