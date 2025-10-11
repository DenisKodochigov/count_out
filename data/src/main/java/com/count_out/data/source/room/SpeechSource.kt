package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData

interface SpeechSource {
    fun update(speech: Data): ResultData<Data>
}
