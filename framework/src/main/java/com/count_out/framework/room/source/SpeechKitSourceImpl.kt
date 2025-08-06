package com.count_out.framework.room.source

import android.database.sqlite.SQLiteConstraintException
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.count_out.framework.room.db.speech_kit.SpeechKitTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechKitSourceImpl @Inject constructor(
    private val source: SpeechSourceImpl,
    private val dao: SpeechKitDao,
) : SpeechKitSource, PrimeSource() {

    override fun get(speechKit: TypeSource): Flow<ResultSource<TypeSource>> =
        if (speechKit is TypeSource.SpeechKitT) {
            dao.get(speechKit.item.idSpeechKit).filterNotNull().map {
                TypeSource.SpeechKitT(item = it.toSpeechKit()) }.resultSource()
        } else flow{ emit(ResultSource.Error(ThrowableDS.NotValidType()))}

    override fun copy(speechKit: TypeSource): ResultSource<TypeSource>{
        return try {
            if (speechKit is TypeSource.SpeechKitT) {
                copy(speechKit.item).let {
                    if (it > 0L) { ResultSource.Success(TypeSource.LongT(item = it))
                    } else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }

    override fun update(speechKit: TypeSource): ResultSource<TypeSource> {
        return try {
            if (speechKit is TypeSource.SpeechKitT) {
                if (updateSpeech(speechKit.item.beforeStart) &&
                    updateSpeech(speechKit.item.afterStart) &&
                    updateSpeech(speechKit.item.beforeEnd) &&
                    updateSpeech(speechKit.item.afterEnd)
                    ) ResultSource.Success(speechKit)
                else ResultSource.Error(ThrowableDS.RequestFailed())
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }

    override fun del(speechKit: TypeSource): ResultSource<TypeSource> {
        return try {
            if (speechKit is TypeSource.SpeechKitT) {
                if (delSpeech(speechKit.item.beforeStart) &&
                    delSpeech(speechKit.item.afterStart) &&
                    delSpeech(speechKit.item.beforeEnd) &&
                    delSpeech(speechKit.item.afterEnd)
                ){
                    dao.del(speechKit.item.idSpeechKit).let {
                        if (it == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                        else ResultSource.Success(TypeSource.IntT(1)) }
                } else ResultSource.Error(ThrowableDS.RequestFailed())
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }

    fun copy(speechKit: SpeechKitImplD): Long {
        return dao.add(
            SpeechKitTable(
                idBeforeStart = copySpeech(speechKit.beforeStart),
                idAfterStart = copySpeech(speechKit.afterStart),
                idBeforeEnd = copySpeech(speechKit.beforeEnd),
                idAfterEnd = copySpeech(speechKit.afterEnd),
            ))
    }
    fun updateSpeech(speech: SpeechImplD?): Boolean{
        return speech?.let{item->
            source.update( TypeSource.SpeechT(item)).resultOK() } ?: false
    }
    fun delSpeech(speech: SpeechImplD?): Boolean {
        return speech?.let{item->
            source.del( TypeSource.SpeechT(item)).resultOK() } ?: false
    }
    fun copySpeech(speech: SpeechImplD?): Long {
        val item = if(speech != null && speech.idSpeech > 0L) speech else SpeechImplD()
        return source.copy(TypeSource.SpeechT(item)).let{
            if (it is ResultSource.Success && it.data is TypeSource.LongT)
                    (it.data as TypeSource.LongT).item else 0L
        }
    }

}
//    =
//        if (speechKit is TypeSource.SpeechKitT) {
//            copyValue(SpeechKitImplD( speechKit.item))?.let {id->
//                dao.get(id).filterNotNull().map {
//                    TypeSource.SpeechKitT(item = it.toSpeechKit()) }.resultSource()
//            } ?: flow { emit (ResultSource.Error(ThrowableDS.ReturnNull())) }
//        } else flow { emit (ResultSource.Error(ThrowableDS.NotValidType())) }

//    {
//        return flow { emit (
//            if (speechKit is TypeSource.SpeechKitT) {
//                speechKit.item.beforeStart?.let { source.updateValue( it as SpeechImplD) }
//                speechKit.item.afterStart?.let { source.updateValue( it as SpeechImplD) }
//                speechKit.item.beforeEnd?.let { source.updateValue( it as SpeechImplD) }
//                speechKit.item.afterEnd?.let { source.updateValue( it as SpeechImplD) }
//                wrapResult{ speechKit }
//            } else ResultSource.Error(ThrowableDS.NotValidType())
//        )}
//    }