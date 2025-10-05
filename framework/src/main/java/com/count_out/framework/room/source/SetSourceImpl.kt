package com.count_out.framework.room.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Companion.flatMap
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.result
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.speech.SpeechTb
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechSource: SpeechSourceImpl,
    private val dao: SetDao
): SetSource, PrimeSource() {

    override fun copy(set: TypeSource): ResultSource<TypeSource> =
        set.useResult { setTb ->
            dao.insert( setTb.copy(idSet = 0L)).result().flatMap { ownerId->
                val listSpeech = speechSource.getListSpeech(setId = setTb.idSet)
                        .map { it.apply { setId = ownerId.item } }
                if (speechSource.insert(listSpeech).count() == listSpeech.count())
                    ResultSource.Success(TypeSource.IntT(listSpeech.count()))
                else ResultSource.Error(ThrowableDS.RequestFailed())
            }
        }

    override fun del(set: TypeSource): ResultSource<TypeSource> =
        set.use { item -> dao.delete(item).toLong() }

    override fun update(set: TypeSource): ResultSource<TypeSource> =
        set.use { setTb -> dao.update(setTb).toLong() }

//##############################################################################################
    inline fun TypeSource.use(crossinline block: (SetTb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.SetT) {
            try { block(this.item as SetTb).result() }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    inline fun TypeSource.useResult(crossinline block: (SetTb) -> ResultSource<TypeSource>): ResultSource<TypeSource> =
        if (this is TypeSource.SetT) {
            try { block(this.item as SetTb) }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

}