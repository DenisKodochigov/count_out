package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.models.types_data.LongDb

interface SpeechSource {
    fun update(speech: Data): ResultData<Data>
}
