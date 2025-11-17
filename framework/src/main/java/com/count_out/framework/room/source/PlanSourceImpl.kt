package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.NameIdDb
import com.count_out.data.models.entity.PartDb
import com.count_out.data.models.entity.PlanDb
import com.count_out.data.models.entity.PlansDb
import com.count_out.data.models.entity.SetViewIdDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.PartSource
import com.count_out.data.source.room.PlanSource
import com.count_out.framework.room.db.part.PartTb.Companion.toTb
import com.count_out.framework.room.db.plan.PlanDao
import com.count_out.framework.room.db.plan.PlanTb
import com.count_out.framework.room.db.plan.PlanTb.Companion.toTb
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

    override fun gets(): Flow<ResultData<Data>> = dao.getPlans()
        .filterNotNull()
        .map { list ->
            if (list.isEmpty()) ResultData.Error(ThrowableDS.ErrorPlans())
            else ResultData.Success(PlansDb(list.map { it.toTable() }))}
        .flowOn(Dispatchers.Default)
        .catch { emit(ResultData.Error(ThrowableDS.extract(it))) }

    override fun get(idPlan: Data): Flow<ResultData<Data>> =
        idPlan.safeUseFlow<LongDb, PlanTb>{ planId->
            dao.getPlan(planId.item).filterNotNull().map { it.toTable()} }

    override fun getId(idPlan: Data): Flow<ResultData<Data>> =
        idPlan.safeUseFlow<LongDb, PlanTb> {
            id-> dao.getPlan(id.item).filterNotNull().map{ it.toTable() } }

    override fun copy (plan: Data): ResultData<Data> = runCatching {
        if (plan is PlanDb){
            copyWithDependencies(
                insertMain = { dao.insert(plan.toTb().copy(idPlan = 0L)) },
                getSpeeches = {idNew-> speechSource.getListSpeech(planId = plan.idPlan)
                    .map{ item-> item.apply{ planId = idNew} }},
                insertSpeeches = { speechSource.insert(it) },
                copyNested = { id -> copyParts(plan.parts, id)}
            )
        } else ResultData.Error(ThrowableDS.ErrorTypePlan())
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(plan: Data): ResultData<Data> =
        plan.safeUse<PlanDb, Long> { dao.delete( it.toTb()).toLong() }

    override fun update(nameId: Data): ResultData<Data> =
        nameId.safeUse<NameIdDb, Long> { dao.updateName(it.name, it.id).toLong() }

    override fun changeSequence(setViewId: Data): ResultData<Data> {
        return try {
            if (setViewId is SetViewIdDb) {
                val from = setViewId.from
                val to = setViewId.to
                val list = dao.gets()
                if (from > to) for ( id in to..< from){ list[id].idView = (id + 1) }
                else for ( id in (from + 1)..to){ list[id].idView = (id - 1)}
                list[from].idView = to
                if (dao.update(list) == list.size) ResultData.Success(LongDb(list.size.toLong()))
                else ResultData.Error(ThrowableDS.ErrorExercises())
            } else ResultData.Error(ThrowableDS.ErrorTypeSetIdView())
        } catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
    }

    fun copyParts(parts: List<PartDb>, id: Long): ResultData<LongDb> =
        if (parts.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            parts.map { pr-> partSource.copy((pr.toTb()).apply{ this.planId = id}) }
            ResultData.Success(LongDb(parts.size.toLong()))
        }
}

//    override fun copy9(plan: Data): ResultData<Data> =
//        plan.safeUse<PlanTb, ResultData<Data>> { planTb->
//            var idNew = LongDb(0L)
//            dao.insert(planTb.copy(idPlan = 0L)).longToResult()
//                .flatMap { ownerId->
//                    idNew = ownerId as LongDb
//                    val listSpeech = speechSource.getListSpeech( planId = planTb.idPlan)
//                        .map { it.apply { planId = ownerId.item } }
//                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
//                        ResultData.Success(LongDb(listSpeech.count().toLong()))
//                    else ResultData.Error(ThrowableDS.RequestFailed()) }
//                .flatMap { copyParts(planTb.parts, ownerId = idNew) }
//        }