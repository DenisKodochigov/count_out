package com.example.count_out.ui.view_components.drag_drop_column.column

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.ui.view_components.lg
import kotlin.math.round

class StateDragColumn(
    val heightList: MutableState<Int> = mutableIntStateOf(0),
    var sizeList: Int = 0,
) {
    private var offsetBegin: Float = 0f
    private val offsetY_: MutableState<Float> = mutableFloatStateOf(0f)
    private val offsetY: MutableState<Float> = mutableFloatStateOf(0f)
    private val offsetZ: MutableState<Float> = mutableFloatStateOf(0f)
    private var droppedOffsetFinal: Int = 0
    private var heightItem: Int = 0
    private var indexItem: Int = 0
    private var indexTo: Int = 0
    @SuppressLint("MutableCollectionMutableState")
    val mapOffset: MutableState<MutableMap<Int, Int>> = mutableStateOf( mutableMapOf())
    init {
        for ( i in 0..< sizeList) mapOffset.value[i] = 0
    }
    fun onStartDrag(offset: Float) {
        offsetBegin = offset
        if (heightList.value > 0 && sizeList > 0) {
            heightItem = round((heightList.value / sizeList).toDouble()).toInt()
            offsetZ.value = 1.0f
            indexItem = round(offset / (heightList.value / sizeList)).toInt()
        }
    }
    fun itemOffset(index: Int): Int {
        val addOffset = mapOffset.value[index] ?: 0
        return  addOffset + if (index == indexItem) offsetY.value.toInt() else 0
    }

    fun itemZ(index: Int) = if (index == indexItem) offsetZ.value else 0f

    //#################################################################

    fun onDrag(delta: Float){
        if ((offsetBegin + offsetY.value + delta) in 0f..(heightList.value - heightItem).toFloat()) {
            offsetY.value += delta
            offsetY_.value += delta
            if (offsetY_.value > heightItem) {
                indexTo = indexItem+1
                mapOffset.value[indexTo] = -heightItem
                droppedOffsetFinal += heightItem
                offsetY_.value = 0f
//                lg("indexItemL $indexItemL mapOffset:${mapOffset}")
            } else if (offsetY_.value < (-1) * heightItem){
                indexTo = indexItem - 1
                mapOffset.value[indexTo] = heightItem
                droppedOffsetFinal -= heightItem
                offsetY_.value = 0f
//                lg(" move ${indexItemL + 1} to $indexItemL  offsetBegin:$offsetBegin")
            }
        }
    }
    fun onStopDrag( onMoveItem: (Int, Int) -> Unit){
        offsetY.value =0f
        offsetZ.value = 0.0f
        for ( i in 0..< sizeList) mapOffset.value[i] = 0
        onMoveItem(indexItem,indexTo)
        lg(" indexItem $indexItem  indexTo $indexTo")
    }
}

fun roundMy( value: Float): Int{
    return if (value > 0) {
        if (value - value.toInt() >= 0.5 ) { value.toInt() + 1 }
        else { value.toInt() }
    } else {
        if (value - value.toInt() >= -0.5 ) { value.toInt() }
        else { value.toInt() - 1 }
    }
}