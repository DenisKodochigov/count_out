package com.count_out.framework.room.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.result
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTb
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechKitSource: SpeechKitSourceImpl,
    private val dao: SetDao
): SetSource, PrimeSource() {

    override fun copy(set: TypeSource): ResultSource<TypeSource> =
        set.use { setTb ->
            val newId = (speechKitSource.insert(setTb.speechId))
                .getTyped<TypeSource.LongT>()
                ?.item ?: return@use 0L
            dao.insert(setTb.copy(idSet = 0L, speechId = newId))
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

}