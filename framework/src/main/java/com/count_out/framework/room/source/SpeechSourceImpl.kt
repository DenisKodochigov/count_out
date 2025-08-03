package com.count_out.framework.room.source

import android.database.sqlite.SQLiteConstraintException
import android.util.Log.e
import com.count_out.data.models.SpeechImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SpeechSource
import com.count_out.framework.room.db.speech.SpeechDao
import com.count_out.framework.room.db.speech.SpeechTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpeechSourceImpl @Inject constructor(private val dao: SpeechDao): SpeechSource, PrimeSource() {

    override fun get(id: TypeSource): Flow<ResultSource<TypeSource>> {
        return if (id is TypeSource.LongT) {
            dao.getF(id.item).filterNotNull().map {
                TypeSource.SpeechT(item = it.toSpeech()) }.resultSource()
        } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
    }

    override fun copy(speech: TypeSource): ResultSource<TypeSource> {
        return try {
            if (speech is TypeSource.SpeechT) {
                dao.add(SpeechTable(SpeechImplD(speech.item))).let {
                    if (it > 0L) { ResultSource.Success(TypeSource.LongT(item = it))
                    } else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }
//
    override fun del(speech: TypeSource): ResultSource<TypeSource> {
        return try {
            if (speech is TypeSource.SpeechT) {
                dao.del(speech.item.idSpeech).let {
                    if (it > 0) { ResultSource.Success(TypeSource.IntT(item = it))
                    } else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }

    override fun update(speech: TypeSource): ResultSource<TypeSource> {
        return try {
            if (speech is TypeSource.SpeechT) {
                dao.update(SpeechTable(SpeechImplD(speech.item))).let {
                    if (it > 0) { ResultSource.Success(TypeSource.IntT(item = it))
                    } else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }
//    fun getValue(id: Long) = dao.get(id)
//    fun delValue(id: Long) = dao.del(id)
//    fun copyValue(speech: SpeechImplD) = dao.add(SpeechTable(speech))

}

//        val result1 = getResult { copyValue(speech) }
//        return if (result1 is ResultSource.Success){
//                 dao.getF(result.data).map { it?.toSpeech() }.resultSource()
//                } else flow { emit (result as ResultSource.Error) }

//    override fun copy(speech: TypeSource): Flow<ResultSource<TypeSource>> {//SpeechImplD
//        return if (speech is TypeSource.SpeechT) {
//            copyValue(SpeechImplD( speech.item)).let { id->
//                dao.getF(id).filterNotNull().map {
//                    TypeSource.SpeechT(item = it.toSpeech()) }.resultSource()
//            }
//        } else flow { emit (ResultSource.Error(ThrowableDS.NotValidType())) }
//    }
//    override fun del(speech: TypeSource): Flow<ResultSource<TypeSource>> {//SpeechImplD
//        return if (speech is TypeSource.SpeechT) {
//            flow {
//                emit (when(val result = delValue(speech.item.idSpeech)){
//                    0-> ResultSource.Error(ThrowableDS.RequestFailed())
//                    else-> ResultSource.Success(data = TypeSource.IntT(item = result))
//            })}}
//        else flow { emit (ResultSource.Error(ThrowableDS.NotValidType())) }
//    }
//
//    override fun update(speech: TypeSource): Flow<ResultSource<TypeSource>> {//SpeechImplD
//        return if (speech is TypeSource.SpeechT) {
//            updateValue(SpeechImplD(speech.item)).let{ result->
//                if (result > 0) {
//                    dao.getF(speech.item.idSpeech).filterNotNull().map {
//                        TypeSource.SpeechT(item = it.toSpeech()) }.resultSource()
//                } else flow { emit(ResultSource.Error(ThrowableDS.ReturnNull())) }
//            }}
//        else flow { emit (ResultSource.Error(ThrowableDS.NotValidType())) }
//    }