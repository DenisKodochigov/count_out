package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.SpeechDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.source.room.SpeechSource
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTb
import javax.inject.Inject

class SpeechSourceImpl @Inject constructor(private val dao: SpeechDao): SpeechSource, PrimeSource() {

    override fun update(speech: Data): ResultData<Data> {
        return speech.use { item-> dao.update(item).toLong() }
    }

//    fun getForKit(idKit: Long) = dao.getForKit(idKit)
    fun getSpeeches(setId: Long? = null, exerciseId: Long? = null, ringId: Long? = null,
                    partId: Long? = null, planId: Long? = null) =
        dao.getSpeeches(setId, exerciseId, ringId, partId, planId)

    fun insert(speeches: List<SpeechTb>): List<Long> = dao.insert(speeches)
    fun insert(speech: SpeechTb): Long = dao.insert(speech)

    inline fun Data.use(crossinline block: (SpeechTb) -> Long): ResultData<Data> =
        if (this is SpeechDb) {
            try {
                block(this as SpeechTb).let {
                if (it > 0) ResultData.Success(LongDb(item = it))
                else ResultData.Error(ThrowableDS.RequestFailed())
            } }
            catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
        } else ResultData.Error(ThrowableDS.NotValidType())

    fun getListSpeech(
        setId: Long? = null, exerciseId: Long? = null, ringId: Long? = null, partId: Long? = null, planId: Long? = null
    ): List<SpeechTb> {
        return if (setId!! > 0 || exerciseId!! > 0 || ringId!! > 0 || partId!! > 0 || planId!! > 0 ){
            try {
                getSpeeches(setId, exerciseId, ringId, partId, planId)
                    .filterNot { it == emptyList<SpeechTb>() }
                    .map { it.apply { idSpeech = 0L } }
            } catch (e: Exception) { List(4) { SpeechTb() } }
        } else { List(4) { SpeechTb() } }
    }
}

