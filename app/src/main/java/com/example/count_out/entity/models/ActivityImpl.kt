package com.example.count_out.entity.models

import com.example.count_out.entity.workout.Activity

data class ActivityImpl(
    override val idActivity: Long,
    override val name: String,
    override val description: String,
    override val icon: Int,
    override val color: Int,
    override val videoClip: String,
    override val audioTrack: String
): Activity
