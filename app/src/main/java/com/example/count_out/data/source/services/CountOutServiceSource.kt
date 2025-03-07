package com.example.count_out.data.source.services

import kotlinx.coroutines.flow.Flow

interface CountOutServiceSource {
    fun bind(): Flow<Boolean>
    fun unbind(): Flow<Boolean>
}