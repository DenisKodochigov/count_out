package com.count_out.framework.room.db.speech

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.count_out.data.models.SpeechImpl

@Entity(tableName = "tb_speech")
data class SpeechTable(
    @PrimaryKey(autoGenerate = true)  var idSpeech: Long = 0,
     var message: String = "",
     var duration: Long = 0L,
    @Ignore  var addMessage: String = "",
){
    fun toSpeech(): SpeechImpl {
        return SpeechImpl(
            idSpeech = this.idSpeech,
            message = this.message,
            duration = this.duration,
            addMessage = this.addMessage
        )
    }
}