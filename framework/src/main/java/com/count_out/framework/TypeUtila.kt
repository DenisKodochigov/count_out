package com.count_out.framework

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.framework.room.db.plan.PlanTb

fun Long.result(): ResultSource<TypeSource.LongT> =
    if (this > 0L) ResultSource.Success(TypeSource.LongT(this))
    else ResultSource.Error(ThrowableDS.RequestFailed())
fun Boolean.result(): ResultSource<TypeSource.BooleanT> =
    if (this > true) ResultSource.Success(TypeSource.BooleanT(this))
    else ResultSource.Error(ThrowableDS.RequestFailed())
fun PlanTb.result(): ResultSource<TypeSource.PlanT> = ResultSource.Success(TypeSource.PlanT(this))
//inline fun <reified TS: TypeSource, DbT> copyEntity(
//    source: TypeSource,
//    speechKitSource: SpeechKitSourceImpl,
//    crossinline getItem: (TS) -> DbT,
//    crossinline getSpeechId: (TS) -> Long?,                  // вернёт speechId или null, если нет
//    crossinline insertObj: (DbT, Long?) -> DbT,                // создание Db объекта с новым speechId
//    crossinline insertDb: (DbT) -> ResultSource<TypeSource.LongT>,
//    crossinline copyChildren: (TS, TypeSource.LongT) -> ResultSource<TypeSource>
//): ResultSource<TypeSource> {
//
//    val typed = source as? TS ?: return ResultSource.Error(ThrowableDS.NotValidType())
//
//    val speechId = getSpeechId(typed)
//
//    val entryResult = speechId?.let { id ->
//        entryPoint { TypeSource.SpeechKitT(SpeechKitTb(idSpeechKit = id)) }
//            .flatMap { speechKit -> speechKitSource.insert(speechKit).asType<TypeSource.LongT>() }
//    } ?: ResultSource.Success(TypeSource.LongT(0L))
//
//    return entryResult.flatMap { idSpeechKit ->
//        val dbObject = insertObj( getItem(typed), idSpeechKit.item.takeIf { it != 0L })
//        insertDb(dbObject)
//    }.flatMap { ownerId ->
//        if (ownerId.item == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
//        else copyChildren(typed, ownerId)
//    }
//}
//fun copy1(part: TypeSource): ResultSource<TypeSource> =
//    copyEntity<TypeSource.PartT, PartTb>(
//        source = part,
//        speechKitSource,
//        getItem = { it.item as PartTb },
//        getSpeechId = { it.item.speechId },
//        insertObj = { item, speechId -> item.apply { this.speechId = speechId!!; this.idPart = 0L } },
//        insertDb = { dao.insert(it).result() },
//        copyChildren = { pr, ownerId -> copyRings(pr.item.rings, ownerId) }
//    )