package com.example.count_out.domain.repository

import kotlinx.coroutines.flow.Flow

interface CountOutServiceRepo {
    fun bind(): Flow<Boolean>
    fun unbind(): Flow<Boolean>
}