package com.example.count_out.ui.view_components.drag_drop_column

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import com.example.count_out.ui.view_components.lg
import javax.inject.Singleton
import kotlin.math.round

@Singleton
class StateDragColumn{
    var heightList: Float = 0f
    var offset: Float = 0f
    var sizeList: Int = 0
    var shift: Animatable<Float, AnimationVector1D> = Animatable(0f)
    private var maxOffsetUp: Float = 0f
    private var maxOffsetDown: Float = 0f
    private var offsetBegin: Float = 0f

    private val heightItem = if (heightList > 0 && sizeList > 0) (heightList / sizeList) else 0f
    fun onStartDrag( indexItem: Int) {
        if (heightList > 0 && sizeList > 0) {
            offsetBegin = indexItem * heightItem
            maxOffsetDown = heightList
        } else {
            lg("onStartDrag heightList:$heightList; sizeList:$sizeList")
        }
        lg("onStartDrag heightList:$heightList; sizeList:$sizeList")
    }
    fun onStopDrag( indexItem: Int, onMoveItem: (Int, Int) -> Unit){
        val moveToIndex = indexItem + round(shift.value / heightItem).toInt()
        onMoveItem( indexItem, moveToIndex )
    }
    suspend fun shiftItem( delta: Float, indexItem: Int ){
        offset += delta
        if ((offsetBegin + shift.value + offset) in maxOffsetUp..maxOffsetDown) {
            shift.snapTo(shift.value + offset)
            shift.value.plus(offset)
        }
        lg("offset: $offset; shift.value: ${shift.value}; delta: $delta; maxOffsetUp: $maxOffsetUp; maxOffsetDown: $maxOffsetDown ")
    }
//    fun itemOffset(index: Int): Float{
//        return if (index == indexItem ) offset else 0f
//    }
}
