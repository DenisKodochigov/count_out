package com.count_out.framework.room.source

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechKitSource: SpeechKitSourceImpl,
    private val dao: SetDao
): SetSource, PrimeSource() {

    override fun get(set: TypeSource): Flow<ResultSource<TypeSource>> =
        try {
            if (set is TypeSource.SetT) {
                dao.get(set.item.idSet).filterNotNull().map { TypeSource.SetT(it.toSet()) }.resultSource()
            } else { flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) } }
        } catch(e: Exception) {
            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }

    override fun gets(exerciseId: TypeSource): Flow<ResultSource<TypeSource>> =
        try {
            if (exerciseId is TypeSource.LongT) {
                dao.gets(exerciseId.item).filterNotNull().map { list ->
                    TypeSource.SetsT(list.filterNotNull().map { it.toSet() }) }.resultSource()
            } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
        } catch(e: Exception) {
            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }

    override fun copy(set: TypeSource): ResultSource<TypeSource> =
        if (set is TypeSource.SetT) {
            try {
                val speechKitId = speechKitSource.copy( TypeSource.SpeechKitT(
                    set.item.speech?.let { SpeechKitImplD(it)} ?: SpeechKitImplD())).resultLong()
                dao.add(SetTable(set.item,0L,speechKitId)).let { count ->
                    if (count > 0) ResultSource.Success(TypeSource.LongT(item = count))
                    else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    override fun del(set: TypeSource): ResultSource<TypeSource> =
        temp(set,{ dao.del(it.idSet) },{ speechKitSource.del(it) })

    override fun update(set: TypeSource): ResultSource<TypeSource> =
        temp(set,{ dao.update(it) },{ speechKitSource.update(it) })

    fun temp(set: TypeSource, action:(SetTable)->Int, actionSp:(TypeSource)->ResultSource<TypeSource>
    ): ResultSource<TypeSource>{
        val result = if (set is TypeSource.SetT) {
            try {
                set.item.speech?.let {
                    actionSp(TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                action(SetTable(set.item)).let { count ->
                    if (count > 0) ResultSource.Success(TypeSource.IntT(item = count))
                    else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())
        return result
    }
}
//        if (set is TypeSource.SetT) {
//            try {
//                set.item.speech?.let {
//                    speechKitSource.update(
//                        TypeSource.SpeechKitT(SpeechKitImplD(it)))}
//                dao.update(SetTable(set.item)).let { count ->
//                    if (count > 0L) ResultSource.Success(TypeSource.IntT(item = count))
//                    else ResultSource.Error(ThrowableDS.RequestFailed())
//                }
//            } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
//        } else ResultSource.Error(ThrowableDS.NotValidType())