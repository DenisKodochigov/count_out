package com.example.count_out.ui.view_components.drag_drop_column

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import javax.inject.Singleton
import kotlin.math.round

@Singleton
data class StateDragColumn(
    val indexItem: Int = 0,
    var offset: Float = 0f,
    val heightList: Float = 1f,
    val sizeList: Int = 0,
    var shift: Animatable<Float, AnimationVector1D> = Animatable(0f),
    var maxOffsetUp: Float = 0f,
    var maxOffsetDown: Float = 0f,
){
    private val heightItem = if (heightList > 0 && sizeList > 0) (heightList / sizeList) else 0f
    fun onStartDrag() {
        if (heightList > 0 && sizeList > 0) {
            offset = indexItem * heightItem
            maxOffsetDown = heightList
        } else {
//            log(true, "onStartDrag heightList:$heightList; sizeList:$sizeList")
        }
//        log(true, "onStartDrag heightList:$heightList; sizeList:$sizeList")
    }
    fun onStopDrag(onMoveItem: (Int, Int) -> Unit){
        val moveToIndex = indexItem + round(shift.value / heightItem).toInt()
        onMoveItem( indexItem, moveToIndex )
    }
    suspend fun shiftItem( delta: Float){
        if ((offset + shift.value + delta) in maxOffsetUp..maxOffsetDown) {
            shift.snapTo(shift.value + delta)
            shift.value.plus(delta)
        }
//        log(true, "offset: $offset; shift.value: ${shift.value}; shiftItem: $delta; ")
    }
}
