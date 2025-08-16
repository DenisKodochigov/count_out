package com.count_out.framework.room.source

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.count_out.data.models.RoundImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.framework.room.db.training.TrainingDao
import com.count_out.framework.room.db.training.TrainingTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingSourceImpl @Inject constructor(
    private val dao: TrainingDao,
    private val roundSource: RoundSource,
    private val ringSource: RingSource,
    private val speechKitSource: SpeechKitSourceImpl,
): TrainingSource, PrimeSource() {

    override fun gets(): Flow<ResultSource<TypeSource>>{
        return try {
            dao.getTrainingsRel().filterNotNull().map { list ->
                TypeSource.PlansT(list.map { it.toTraining() }) }.resultSource()
        } catch(e: Exception) {
            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }
    }

    override fun get(training: TypeSource): Flow<ResultSource<TypeSource>> {
        return try {
            if (training is TypeSource.PlanT) {
                dao.getTrainingRel(training.item.idTraining).filterNotNull()
                    .map { TypeSource.PlanT(it.toTraining()) }.resultSource()
            } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
        } catch(e: Exception) {
            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }
    }

    override fun getId(id: TypeSource): Flow<ResultSource<TypeSource>> {
        return try {
            if (id is TypeSource.LongT) {
                dao.getTrainingRel(id.item).filterNotNull()
                    .map { TypeSource.PlanT(it.toTraining()) }.resultSource()
            } else flow { emit(ResultSource.Error(ThrowableDS.NotValidType())) }
        } catch(e: Exception) {
            flow { emit(ResultSource.Error(ThrowableDS.extract(e)))} }
    }

    override fun copy(training: TypeSource): ResultSource<TypeSource> {
        return try {
            if (training is TypeSource.PlanT) {
                val speechKitTypeSource = TypeSource.SpeechKitT(
                    training.item.speech?.let { it as SpeechKitImplD } ?: SpeechKitImplD())
                speechKitSource.copy(speechKitTypeSource).result { idSpeechKit->
                    if (idSpeechKit is TypeSource.LongT) {
                        dao.add(TrainingTable(training.item, idSpeechKit.item,0L))
                            .let{ id->
                                if (id == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
                                else {
                                    var error = false
                                    training.item.rounds.forEach{ round->
                                        roundSource.copy(
                                            TypeSource.RoundT(
                                                RoundImplD(round, 0L, id))).let{
                                            if (it is ResultSource.Error) {
                                                error = true
                                                return@forEach }
                                        }
                                    }
                                    if (error) ResultSource.Error(ThrowableDS.RequestFailed())
                                    else ResultSource.Success(TypeSource.LongT(id))
                                }
                        }
                    } else ResultSource.Error(ThrowableDS.NotValidType())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }
    override fun del(training: TypeSource): ResultSource<TypeSource> {
        return try {
            if (training is TypeSource.PlanT) {
                var error = false
                training.item.speech?.let {
                    speechKitSource.del(TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                if (training.item.rounds.isNotEmpty()){
                    training.item.rounds.forEach { round ->
                        roundSource.del(TypeSource.RoundT(RoundImplD(round)))
                            .let { result->
                            if (result is ResultSource.Error) {
                                error = true
                                return@forEach
                            }
                        }
                    }
                }
                if (error)ResultSource.Error(ThrowableDS.RequestFailed())
                else {
                    dao.del(training.item.idTraining).let{ result->
                        if (result == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                        else ResultSource.Success(TypeSource.IntT(result))
                    }
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }

    override fun update(training: TypeSource): ResultSource<TypeSource> {
        return try {
            if (training is TypeSource.PlanT) {
                training.item.speech?.let {speechKitSource.update(
                    TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                dao.update(TrainingTable(training.item)).let{result->
                    if (result == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                    else ResultSource.Success(TypeSource.IntT(result))
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }
}