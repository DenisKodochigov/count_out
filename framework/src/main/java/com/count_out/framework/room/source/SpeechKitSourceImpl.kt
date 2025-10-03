package com.count_out.framework.room.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.framework.room.db.speech.SpeechTb
import com.count_out.framework.room.db.speech_kit.SpeechKitDao
import com.count_out.framework.room.db.speech_kit.SpeechKitTb
import javax.inject.Inject

class SpeechKitSourceImpl @Inject constructor(
    private val sourceSpeech: SpeechSourceImpl,
    private val daoKit: SpeechKitDao,
): SpeechKitSource, PrimeSource() {
    override fun insert(speechKitId: Long, idSet: Long?, idExercise: Long?,
                        idRing: Long?, idPart: Long?, idPlan: Long?
    ): ResultSource<TypeSource> {
        val newIdKit = daoKit.insert(SpeechKitTb(idSpeechKit = 0L, setId = idSet,
            exerciseId = idExercise, ringId = idRing, partId = idPart, planId = idPlan))
        val listSpeech = if (speechKitId > 0) {
            sourceSpeech.getForKit(speechKitId)
                .filterNot { it == emptyList<SpeechTb>() }
                .map { item->
                    item.idSpeech = 0
                    item.idKit = newIdKit
                    item }
        } else {
            listOf(SpeechTb(idKit = newIdKit),
                SpeechTb(idKit = newIdKit),
                SpeechTb(idKit = newIdKit),
                SpeechTb(idKit = newIdKit))
        }
        return if (newIdKit > 0){
            if (sourceSpeech.insert(listSpeech).count() == listSpeech.count())
                ResultSource.Success(TypeSource.IntT(listSpeech.count()))
            else ResultSource.Error(ThrowableDS.RequestFailed())
        } else ResultSource.Error(ThrowableDS.RequestFailed())
    }
}
//    override fun insert(speechKit: TypeSource): ResultSource<TypeSource> =
//        speechKit.use{kitTb-> insert(kitTb.idSpeechKit) }
//    inline fun TypeSource.use(crossinline block: (SpeechKitTb) -> ResultSource<TypeSource>): ResultSource<TypeSource> =
//        if (this is TypeSource.SpeechKitT) {
//            try { block(this.item as SpeechKitTb) }
//            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
//        } else ResultSource.Error(ThrowableDS.NotValidType())
//    override fun del(speechKit: TypeSource): ResultSource<TypeSource> {
//        return try {
//            if (speechKit is TypeSource.SpeechKitT) {
//                daoKit.delete(speechKit.item as SpeechKitTb).let {
//                if (it == 0) ResultSource.Error(ThrowableDS.RequestFailed())
//                else ResultSource.Success(TypeSource.IntT(1)) }
//            } else ResultSource.Error(ThrowableDS.NotValidType())
//        } catch(e: Exception) { ResultSource.Error(ThrowableDS.extract(e))}
//    }