package com.count_out.framework.room.db.activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.ActivityImplD

@Entity(tableName = "tb_activity")
data class ActivityTable(
    @PrimaryKey(autoGenerate = true)  val idActivity: Long = 0,
    var name: String = "",
    var description: String = "",
    var icon: Int = 0,
    var color: Int = 0,
    var videoClip: String = "",
    var audioTrack: String = ""
){
    constructor(item: ActivityImplD, idActivity: Long = item.idActivity): this(
        idActivity = idActivity,
        name = item.name,
        description = item.description,
        icon = item.icon,
        color = item.color,
        videoClip = item.videoClip,
        audioTrack = item.audioTrack
    )

    fun toActivity() = ActivityImplD(
        idActivity = this.idActivity,
        name = this.name,
        description = this.description,
        icon = this.icon,
        color = this.color,
        videoClip = this.videoClip,
        audioTrack = this.audioTrack
    )
}