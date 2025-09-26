package com.count_out.framework.room.db.old.relation

//data class SpeechKitRelo(
//    @Embedded val speechKitTable: SpeechKitTable,
////    @Relation (parentColumn = "idBeforeStart", entityColumn = "idSpeech", entity = SpeechTable::class) val beforeStart: SpeechTable?,
////    @Relation (parentColumn = "idAfterStart", entityColumn = "idSpeech", entity = SpeechTable::class) val afterStart: SpeechTable?,
////    @Relation (parentColumn = "idBeforeEnd", entityColumn = "idSpeech", entity = SpeechTable::class) val beforeEnd: SpeechTable?,
////    @Relation (parentColumn = "idAfterEnd", entityColumn = "idSpeech", entity = SpeechTable::class) val afterEnd: SpeechTable?,
//){
//    fun toSpeechKit(): SpeechKitImplD {
//        return SpeechKitImplD(
//            idSpeechKit = speechKitTable.idSpeechKit,
////            beforeStart = beforeStart?.toSpeech(),
////            afterStart = afterStart?.toSpeech(),
////            beforeEnd = beforeEnd?.toSpeech(),
////            afterEnd = afterEnd?.toSpeech(),
//        )
//    }
//}
//
//data class SetRelo(
//    @Embedded val setTable: SetTable,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?
//){
//    fun toSet(): SetImplD {
//        return SetImplD(
//            idSet = setTable.idSet,
//            name = setTable.name,
//            speechId = setTable.speechId,
//            goal = Goal.entries[setTable.goal],
//            exerciseId = setTable.exerciseId,
//            reps = setTable.reps,
//            duration = ParameterImpl(value = setTable.duration1, Units.entries[setTable.durationU] ),
//            distance = ParameterImpl(value = setTable.distance, Units.entries[setTable.distanceU] ),
//            weight = ParameterImpl(value = setTable.weight, Units.entries[setTable.weightU] ),
//            intervalReps = setTable.intervalReps,
//            intensity = Zone.entries[setTable.intensity],
//            intervalDown = setTable.intervalDown,
//            groupCount = setTable.groupCount,
//            rest = ParameterImpl(value = setTable.timeRest, Units.entries[setTable.timeRestU] ),
//            speech = speechKit?.toSpeechKit(),
//        )
//    }
//}
//data class ExerciseRelo(
//    @Embedded val exerciseTable: ExerciseTable,
//    @Relation(parentColumn = "activityId", entityColumn = "idActivity", entity = ActivityTable::class) val activity: ActivityTable?,
//    @Relation(parentColumn = "idExercise", entityColumn = "exerciseId", entity = SetTable::class) val sets: List<SetRelo>?,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?
//){
//    fun toExercise(): ExerciseImplD {
//        return ExerciseImplD(
//            idExercise = exerciseTable.idExercise,
//            roundId = exerciseTable.roundId,
//            ringId = exerciseTable.ringId,
//            activityId = exerciseTable.activityId,
//            idView = exerciseTable.idView,
//            activity = activity?.toActivity(),
//            speech = speechKit?.toSpeechKit(),
//            speechId = exerciseTable.speechId,
//            sets = sets?.map { it.toSet() } ?: emptyList(),
//            amountSet = sets?.count() ?: 0,
//            duration = sumSets(sets)
//        )
//    }
//    fun sumSets(sets: List<SetRelo>?): Int {
//        var summ = 0
//        sets?.let { item->
//            item.forEach { set->summ += countTime(set)}
//        }
//        return summ
//    }
//}
//data class RoundRelo(
//    @Embedded val round: RoundTable,
//    @Relation(parentColumn = "idRound", entityColumn = "roundId", entity = ExerciseTable::class) val exercise: List<ExerciseRelo>?,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?,
//){
//    fun toRound(): RoundImplD {
//        return RoundImplD(
//            exercise = exercise?.map{ exercise-> exercise.toExercise()}?.sortedBy{ it.idView } ?: emptyList(),
//            idRound = round.idRound,
//            roundType = RoundType.entries[round.roundType],
//            speechId = round.speechId,
//            numberLaps = round.numberLaps,
//            speech = speechKit?.toSpeechKit(),
//            trainingId = round.trainingId,
//            amount = exercise?.count() ?: 0,
//            duration = ParameterImpl(sumExercise(exercise), Units.M),
//        )
//    }
//    fun sumExercise(exercises: List<ExerciseRelo>?): Double {
//        var summ = 0
//        exercises?.let { items->
//            items.forEach { exercise->
//                exercise.sets?.forEach { set-> summ += countTime(set) } ?: 0.0
//            }
//        }
//        return summ/60.0
//    }
//}
//data class RingRelo(
//    @Embedded val ring: RingTb,
//    @Relation(parentColumn = "idRing", entityColumn = "ringId", entity = ExerciseTable::class) val exercise: List<ExerciseRelo>?,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?,
//){
//    fun toRing(): RingDImpl {
//        return RingDImpl(
//            idRing = ring.idRing,
//            partId = ring.partId,
//            speechId = ring.speechId,
//            speech = speechKit?.toSpeechKit(),
//            exercises = exercise?.map{ exercise-> exercise.toExercise()}?.sortedBy{ it.idView } ?: emptyList(),
//            amount = exercise?.count() ?: 0,
//            duration = ParameterImpl(sumExercise(exercise), Units.M),
//            numberLaps = ring.numberLaps,
//        )
//    }
//    fun sumExercise(exercises: List<ExerciseRelo>?): Double {
//        var summ = 0
//        exercises?.let { items->
//            items.forEach { exercise->
//                exercise.sets?.forEach { set-> summ += countTime(set) } ?: 0.0
//            }
//        }
//        return summ/60.0
//    }
//}
//
//data class TrainingRelo(
//    @Embedded val training: TrainingTable,
//    @Relation(parentColumn = "idTraining", entityColumn = "trainingId", entity = RoundTable::class) val rounds: List<RoundRelo>?,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?,
//){
//    fun toTraining(): TrainingImplD {
//        var amountActivity = 0
//        this.rounds?.forEach { round-> amountActivity += round.exercise?.count() ?:0 }
//        return TrainingImplD(
//            idTraining = training.idTraining,
//            amountActivity = amountActivity,
//            name = training.name,
//            rounds = rounds?.map { it.toRound() } ?: emptyList(),
//            speech = speechKit?.toSpeechKit(),
//            speechId = training.speechId,
//        )
//    }
//}
//data class WorkupRelo(
//    @Embedded val part: PartTb,
//    @Relation(parentColumn = "idPart", entityColumn = "partId", entity = RingTb::class) val rings: List<RingRelo>?,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?,
//){
//    fun toPart(): PartDImpl {
//        return PartDImpl(
//            idPart = part.idPart,
//            planId = part.planId,
//            speechId = part.speechId,
//            speech = speechKit?.toSpeechKit(),
//            name = PartName.entries[part.name],
//            rings = rings?.map { it.toRing() } ?: emptyList(),
//            amount = 0,
//            duration = ParameterImpl(sumExercise(rings), Units.M),
//        )
//    }
//    fun sumExercise(ringsRel: List<RingRelo>?): Double {
//        var summ = 0
//        ringsRel.takeIf{ !it.isNullOrEmpty() }?.forEach { ring->
//            ring.exercise.takeIf{ !it.isNullOrEmpty() }?.forEach { exercise->
//                exercise.sets?.forEach { set-> summ += countTime(set) } ?: 0.0
//            }
//        }
//        return summ/60.0
//    }
//}
//data class WorkdownRelo(
//    @Embedded val part: PartTb,
//    @Relation(parentColumn = "idPart", entityColumn = "partId", entity = RingTb::class) val rings: List<RingRelo>?,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?,
//){
//    fun toPart(): PartDImpl {
//        return PartDImpl(
//            idPart = part.idPart,
//            planId = part.planId,
//            speechId = part.speechId,
//            speech = speechKit?.toSpeechKit(),
//            name = PartName.entries[part.name],
//            rings = rings?.map { it.toRing() } ?: emptyList(),
//            amount = 0,
//            duration = ParameterImpl(sumExercise(rings), Units.M),
//        )
//    }
//    fun sumExercise(ringsRel: List<RingRelo>?): Double {
//        var summ = 0
//        ringsRel.takeIf{ !it.isNullOrEmpty() }?.forEach { ring->
//            ring.exercise.takeIf{ !it.isNullOrEmpty() }?.forEach { exercise->
//                exercise.sets?.forEach { set-> summ += countTime(set) } ?: 0.0
//            }
//        }
//        return summ/60.0
//    }
//}
//data class WorkoutRelo(
//    @Embedded val part: PartTb,
//    @Relation(parentColumn = "idPart", entityColumn = "partId", entity = RingTb::class) val rings: List<RingRelo>?,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?,
//){
//    fun toPart(): PartDImpl {
//        return PartDImpl(
//            idPart = part.idPart,
//            planId = part.planId,
//            speechId = part.speechId,
//            speech = speechKit?.toSpeechKit(),
//            name = PartName.entries[part.name],
//            rings = rings?.map { it.toRing() } ?: emptyList(),
//            amount = 0,
//            duration = ParameterImpl(sumExercise(rings), Units.M),
//        )
//    }
//    fun sumExercise(ringsRel: List<RingRelo>?): Double {
//        var summ = 0
//        ringsRel.takeIf{ !it.isNullOrEmpty() }?.forEach { ring->
//            ring.exercise.takeIf{ !it.isNullOrEmpty() }?.forEach { exercise->
//                exercise.sets?.forEach { set-> summ += countTime(set) } ?: 0.0
//            }
//        }
//        return summ/60.0
//    }
//}
//
//data class PlanRelo(
//    @Embedded val plan: PlanTb,
//    @Relation(parentColumn = "idPlan", entityColumn = "planId", entity = PartTb::class) val parts: List<PartRel>,
//    @Relation(parentColumn = "idPlan", entityColumn = "planId", entity = PartTb::class) val workout: WorkoutRelo,
//    @Relation(parentColumn = "idPlan", entityColumn = "planId", entity = PartTb::class) val workdown: WorkdownRelo,
//    @Relation(parentColumn = "speechId", entityColumn = "idSpeechKit", entity = SpeechKitTable::class) val speechKit: SpeechKitRelo?,
//){
//    fun toPlan(): PlanDImpl {
//        var amountActivity = 0
//
////        this.rounds?.forEach { round-> amountActivity += round.exercise?.count() ?:0 }
//        return PlanDImpl(
////            idPlan = plan.idPlan,
////            name = plan.name,
////            speechId = plan.speechId,
////            amountActivity = amountActivity,
////            speeches = speechKit?.toSpeechKit(),
////            parts = parts,
//        )
//    }
//}
//
//fun countTime(set: SetRelo): Int{
//    var summ = 0
//    summ = set.setTable.duration1.toInt() * when (set.setTable.durationU) {
//        Units.H.ordinal -> 3600
//        Units.M.ordinal -> 60
//        else -> 1
//    }
//    summ += set.setTable.timeRest.toInt() * when (set.setTable.timeRestU) {
//        Units.H.ordinal -> 3600
//        Units.M.ordinal -> 60
//        else -> 1
//    }
//    summ += set.setTable.intervalReps.toInt() * set.setTable.reps
//    return summ
//}