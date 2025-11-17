package com.count_out.framework.room.db.activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.entity.ActivityDb

@Entity(tableName = "activity_tb")
data class ActivityTb(
    @PrimaryKey(autoGenerate = true) override val idActivity: Long = 0,
    override var idView: Int = 0,
    override var name: String = "",
    override var description: String = "",
    override var icon: Int = 0,
    override var color: Int = 0,
    override var videoClip: String = "",
    override var audioTrack: String = ""
): ActivityDb{
    companion object{
        fun ActivityDb.toTb() = ActivityTb(
            idActivity = this.idActivity,
            name = this.name,
            description = this.description,
            icon = this.icon,
            color = this.color,
            videoClip = this.videoClip,
            audioTrack = this.audioTrack,
        )
    }
}
