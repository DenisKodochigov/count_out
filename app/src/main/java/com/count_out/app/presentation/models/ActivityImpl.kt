package com.count_out.app.presentation.models

import com.count_out.domain.entity.Activity

data class ActivityImpl(
    override val idActivity: Long,
    override val name: String,
    override val description: String,
    override val icon: Int,
    override val color: Int,
    override val videoClip: String,
    override val audioTrack: String
): Activity
