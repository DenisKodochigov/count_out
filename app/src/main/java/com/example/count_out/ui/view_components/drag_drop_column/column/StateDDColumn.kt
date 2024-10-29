package com.example.count_out.ui.view_components.drag_drop_column.column

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import com.example.count_out.ui.view_components.lg
import kotlin.math.round

class StateDDColumn( var sizeList: Int = 0,
) {
    private val offsetY: MutableState<Float> = mutableFloatStateOf(0f)
    private val offsetZ: MutableState<Float> = mutableFloatStateOf(0f)
    val heightItem: MutableState<Int> = mutableIntStateOf(0)
    val heightList: MutableState<Int> = mutableIntStateOf(0)

    private var indexDD: Int = 0
    private var indexMoved: Int = 0
    private var delta: Float = 0f
    private var offsetStart: Int = 0
    private val mapOffset: MutableMap<Int, Int> = mutableMapOf()

    fun onStartDrag(offset: Float) {
        initMap()
        offsetStart = offset.toInt()
        offsetY.value = offset
        offsetZ.value = 1.0f
        indexDD = round(offset / heightItem.value).toInt()
        delta = indexDD * heightItem.value - offset
        indexMoved = indexDD
        lg("indexDD $indexDD onStartDrag offset $offset  heightLst $heightList")
    }
    fun onDrag(delta: Float){
        offsetY.value += delta
        if (offsetY.value in 0.0..((heightList.value + delta).toDouble())) {  //heightItem.value * 0.5
            if (offsetY.value > heightItem.value * (indexMoved + 1) - delta) {
                if (indexMoved + 1 <= sizeList)  indexMoved += 1
                lg("indexDD $indexDD  indexMoved $indexMoved  offsetY ${round(offsetY.value)}  moved:${heightItem.value * (indexMoved + 1) - delta}  map $mapOffset")
                mapOffset[indexMoved] = - heightItem.value
            } else if (offsetY.value < heightItem.value * indexMoved - delta){
                if (indexMoved - 1 >= 0) indexMoved -= 1
                lg("indexDD $indexDD  indexMoved $indexMoved  offsetY ${round(offsetY.value)}  moved:${heightItem.value * indexMoved - delta}  map $mapOffset")
                mapOffset[indexMoved] = heightItem.value
            }
        }
    }
    fun onStopDrag( onMoveItem: (Int, Int) -> Unit){
        offsetY.value =0f
        offsetZ.value = 0.0f
        offsetStart = 0
        initMap()
        onMoveItem(indexDD,indexMoved)
    }

    fun itemZ(index: Int) = if (index == indexDD) offsetZ.value else 0f
    fun itemOffset(index: Int): Int {
        return  (mapOffset[index] ?: 0) + if (index == indexDD) {offsetY.value.toInt() - offsetStart} else 0
    }
    private fun initMap() { for ( i in 0..< sizeList) mapOffset[i] = 0}
}
