package com.count_out.framework.room.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SpeechSource
import com.count_out.domain.entity.workout.Speech
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTb
import javax.inject.Inject

class SpeechSourceImpl @Inject constructor(private val dao: SpeechDao): SpeechSource, PrimeSource() {

    override fun update(speech: TypeSource): ResultSource<TypeSource> {
        return speech.use { item-> dao.update(item).toLong() }
    }

//    fun getForKit(idKit: Long) = dao.getForKit(idKit)
    fun getSpeeches(setId: Long? = null, exerciseId: Long? = null, ringId: Long? = null,
                    partId: Long? = null, planId: Long? = null) =
        dao.getSpeeches(setId, exerciseId, ringId, partId, planId)

    fun insert(speeches: List<SpeechTb>): List<Long> = dao.insert(speeches)
    fun insert(speech: SpeechTb): Long = dao.insert(speech)

    inline fun TypeSource.use(crossinline block: (SpeechTb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.SpeechT) {
            try {
                block(this.item as SpeechTb).let {
                if (it > 0) ResultSource.Success(TypeSource.LongT(item = it))
                else ResultSource.Error(ThrowableDS.RequestFailed())
            } }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    fun getListSpeech(
        setId: Long? = null, exerciseId: Long? = null, ringId: Long? = null, partId: Long? = null, planId: Long? = null
    ): List<SpeechTb> {
        return if (setId!! > 0 || exerciseId!! > 0 || ringId!! > 0 || partId!! > 0 || planId!! > 0 ){
            try {
                getSpeeches(setId, exerciseId, ringId, partId, planId)
                    .filterNot { it == emptyList<SpeechTb>() }
                    .map { it.apply { idSpeech = 0L } }
            } catch (e: Exception) { listOf(SpeechTb(), SpeechTb(), SpeechTb(), SpeechTb()) }
        } else { listOf(SpeechTb(), SpeechTb(), SpeechTb(), SpeechTb()) }
    }
}

