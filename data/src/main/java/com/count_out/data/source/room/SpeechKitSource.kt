package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource

interface SpeechKitSource {
    fun insert(speechKitId: Long, idSet: Long? = null, idExercise: Long? = null,
               idRing: Long? = null, idPart: Long? = null, idPlan: Long? = null): ResultSource<TypeSource>
}