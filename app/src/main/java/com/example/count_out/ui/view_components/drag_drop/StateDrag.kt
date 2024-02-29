package com.example.count_out.ui.view_components.drag_drop

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import javax.inject.Singleton

@Singleton
data class StateDrag(
    var maxOffsetUp: Float = 0f,
    var maxOffsetDown: Float = 0f,
    val indexItem: Int = 0,
    var offset: Float = 0f,
    var offsetA: Animatable<Float, AnimationVector1D> = Animatable(0f),
)