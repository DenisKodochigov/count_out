package com.count_out.framework.room.db.old.speech
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.ForeignKey.Companion.NO_ACTION
//import androidx.room.Ignore
//import androidx.room.PrimaryKey
//import com.count_out.data.models.SpeechImplD
//import com.count_out.framework.room.db.old.speech_kit.SpeechKitTable

//@Entity(tableName = "tb_speech",
//    foreignKeys = [ForeignKey(
//        entity = SpeechKitTable::class,
//        parentColumns = ["idSpeechKit"],
//        childColumns = ["idKit"],
//        onDelete = ForeignKey.CASCADE,
//        onUpdate = NO_ACTION
//    )]
//)
//data class SpeechTable(
//    @PrimaryKey(autoGenerate = true)  var idSpeech: Long = 0,
//    @ColumnInfo(index = true, name = "idKit") var idKit: Long = 0,
//    @ColumnInfo(name = "message") var message: String = "",
//    @ColumnInfo(name = "duration") var duration: Long = 0L,
//    @Ignore  var addMessage: String = "",
//){
//    constructor(speech: SpeechImplD, id: Long = speech.idSpeech): this(
//        idSpeech = id,
//        message = speech.message,
//        duration = speech.duration,
//        addMessage = speech.addMessage,
//    )
//
//    fun toSpeech(): SpeechImplD {
//        return SpeechImplD(
//            idSpeech = this.idSpeech,
//            message = this.message,
//            duration = this.duration,
//            addMessage = this.addMessage
//        )
//    }
//}
