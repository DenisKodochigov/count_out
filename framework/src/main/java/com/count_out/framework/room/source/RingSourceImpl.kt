package com.count_out.framework.room.source

import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RingSourceImpl @Inject constructor(
    private val dao: RingDao,
    private val source: ExerciseSource,
    private val speechKitSource: SpeechKitSourceImpl,
): RingSource, PrimeSource() {

    override fun copy(ring: TypeSource): ResultSource<TypeSource> {
        return try {
            if (ring is TypeSource.RingT) {
                val speechKitTypeSource = TypeSource.SpeechKitT(
                    ring.item.speech?.let { it as SpeechKitImplD } ?: SpeechKitImplD())
                speechKitSource.copy(speechKitTypeSource).result { idSpeechKit->
                    if (idSpeechKit is TypeSource.LongT) {
                        dao.add(RingTable(ring.item, idSpeechKit.item,0L))
                            .let{id->
                                if (id == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
                                else if (ring.item.exercise.isNotEmpty()){
                                    var error = false
                                    ring.item.exercise.forEach { exercise ->
                                        source.copy(
                                            TypeSource.ExerciseT(
                                                ExerciseImplD(exercise))).let{
                                            if (it is ResultSource.Error) {
                                                error = true
                                                return@forEach
                                            } }
                                    }
                                    if (error) ResultSource.Error(ThrowableDS.RequestFailed())
                                    else ResultSource.Success(TypeSource.LongT(id))
                                } else source.copy(
                                    TypeSource.ExerciseT(ExerciseImplD(ringId = id)))
                            }
                    }
                    else ResultSource.Error(ThrowableDS.NotValidType())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }
    override fun del(ring: TypeSource):ResultSource<TypeSource> {
        return try {
            if (ring is TypeSource.RingT) {
                var error = false
                ring.item.speech?.let {
                    speechKitSource.del(TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                if (ring.item.exercise.isNotEmpty()){
                    ring.item.exercise.forEach { exercise ->
                        source.del(TypeSource.ExerciseT(
                            ExerciseImplD(exercise))).let { result->
                            if (result is ResultSource.Error) {
                                error = true
                                return@forEach
                            } }
                    }
                }
                if (error)ResultSource.Error(ThrowableDS.RequestFailed())
                else {
                    dao.del(ring.item.idRing).let{result->
                        if (result == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                        else ResultSource.Success(TypeSource.IntT(result))
                    }
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }
    override fun update(ring: TypeSource):ResultSource<TypeSource> {
        return try {
            if (ring is TypeSource.RingT) {
                ring.item.speech?.let {speechKitSource.update(
                    TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                dao.update(RingTable(ring.item)).let{result->
                    if (result == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                    else ResultSource.Success(TypeSource.IntT(result))
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }
}
//    override fun get(ring: TypeSource): Flow<ResultSource<TypeSource>> =
//        try {
//            if (ring is TypeSource.RingT) {
//                dao.get(ring.item.idRing).filterNotNull().map { TypeSource.RingT(it.toRing()) }.resultSource()
//            } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
//        } catch(e: Exception) {
//            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }
//
//    override fun gets(trainingId: TypeSource): Flow<ResultSource<TypeSource>> =
//        try {
//            if (trainingId is TypeSource.LongT) {
//                dao.gets(trainingId.item).filterNotNull().map { list ->
//                    TypeSource.RingsT(list.map { it.toRing() }) }.resultSource()
//            } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
//        } catch(e: Exception) {
//            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }