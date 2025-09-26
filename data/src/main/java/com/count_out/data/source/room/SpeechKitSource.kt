package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

interface SpeechKitSource {
    fun insert(speechKitId: Long): ResultSource<TypeSource>
}