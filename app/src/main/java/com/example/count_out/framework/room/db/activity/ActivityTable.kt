package com.example.count_out.framework.room.db.activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.models.ActivityImpl

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
    fun toActivity() = ActivityImpl(
        idActivity = this.idActivity,
        name = this.name,
        description = this.description,
        icon = this.icon,
        color = this.color,
        videoClip = this.videoClip,
        audioTrack = this.audioTrack
    )
}