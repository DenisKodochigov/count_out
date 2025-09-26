package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource

interface SpeechSource {
    fun update(speech: TypeSource): ResultSource<TypeSource>
}
