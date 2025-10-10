package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.NameIdDb
import com.count_out.data.models.PartDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ResultData.Companion.flatMap
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.PartSource
import com.count_out.data.source.room.PlanSource
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.plan.PlanDao
import com.count_out.framework.room.db.plan.PlanTb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlanSourceImpl @Inject constructor(
    private val dao: PlanDao,
    private val partSource: PartSource,
    private val speechSource: SpeechSourceImpl,
): PlanSource, PrimeSource() {

    override fun gets(): Flow<ResultData<Data>> =
        dao.getPlans()
        .filterNotNull()
        .map { list ->
            if (list.isEmpty()) ResultData.Error(ThrowableDS.RequestFailed())
            else ResultData.Success(list.map { it.toTable() } as Data)}
        .flowOn(Dispatchers.Default)
        .catch { emit(ResultData.Error(ThrowableDS.extract(it))) }

    override fun get(plan: Data): Flow<ResultData<Data>> =
        plan.safeUseFlow<PlanTb, PlanTb>{ plan->
            dao.getPlan(plan.idPlan).filterNotNull().map { it.toTable() } }

    override fun getId(idPlan: Data): Flow<ResultData<Data>> =
        idPlan.safeUseFlow<LongDb, PlanTb> {
            id-> dao.getPlan(id.item).filterNotNull().map{ it.toTable() } }

    override fun copy(plan: Data): ResultData<Data> =
        plan.safeUse<PlanTb, ResultData<Data>> { planTb->
            var idNew = LongDb(0L)
            dao.insert(planTb.copy(idPlan = 0L)).result()
                .flatMap { ownerId->
                    idNew = ownerId as LongDb
                    val listSpeech = speechSource.getListSpeech( planId = planTb.idPlan)
                        .map { it.apply { planId = ownerId.item } }
                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
                        ResultData.Success(LongDb(listSpeech.count().toLong()))
                    else ResultData.Error(ThrowableDS.RequestFailed()) }
                .flatMap { copyParts(planTb.parts, ownerId = idNew) }
        }

    override fun del(plan: Data): ResultData<Data> =
        plan.safeUse<PlanTb, Long> { dao.delete( it).toLong() }

    override fun update(nameId: Data): ResultData<Data> =
        nameId.safeUse<NameIdDb, Long> { dao.updateName(it.name, it.id).toLong() }


    fun copyParts(parts: List<PartDb>, ownerId: LongDb): ResultData<Data> =
        if (parts.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            parts.map { pr-> partSource.copy((pr as PartTb).apply{ this.planId = ownerId.item}) }
                .firstOrNull {it is ResultData.Error} ?: ResultData.Success(LongDb(parts.size.toLong()))
        }
}