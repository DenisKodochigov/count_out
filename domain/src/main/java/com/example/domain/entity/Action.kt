package com.example.domain.entity

import kotlinx.coroutines.flow.Flow

interface Action {
    fun <T>gets(): Flow<List<T>>
    fun <T>get(id: Long): Flow<T>
    fun <T>get(): Flow<T>
    fun del(id: Long)
    fun <T>copy(id: Long): Flow<T>
    fun <T>add(item: T): Flow<T>
    fun <T>update(item: T): Flow<T>
}