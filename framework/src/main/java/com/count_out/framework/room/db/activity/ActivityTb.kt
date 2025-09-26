package com.count_out.framework.room.db.activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.count_out.data.models.ActivityDb

@Entity(tableName = "tb_activity")
data class ActivityTb(
    @PrimaryKey(autoGenerate = true) override val idActivity: Long = 0,
    override var name: String = "",
    override var description: String = "",
    override var icon: Int = 0,
    override var color: Int = 0,
    override var videoClip: String = "",
    override var audioTrack: String = ""
): ActivityDb {
//    constructor(item: ActivityImplD, idActivity: Long = item.idActivity): this(
//        idActivity = idActivity,
//        name = item.name,
//        description = item.description,
//        icon = item.icon,
//        color = item.color,
//        videoClip = item.videoClip,
//        audioTrack = item.audioTrack
//    )
//
//    fun toActivity() = ActivityImplD(
//        idActivity = this.idActivity,
//        name = this.name,
//        description = this.description,
//        icon = this.icon,
//        color = this.color,
//        videoClip = this.videoClip,
//        audioTrack = this.audioTrack
//    )
}