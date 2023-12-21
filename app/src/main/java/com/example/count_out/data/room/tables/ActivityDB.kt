package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.count_out.entity.Activity
@Entity(tableName = "tb_activity")
data class ActivityDB(
    @PrimaryKey(autoGenerate = true) override val idActivity: Long = 0,
    override var name: String = "",
    override var icon: Int = 0,
    override var color: Int = 0,
    override var videoClip: String = "",
    override var audioTrack: String = ""
):Activity
