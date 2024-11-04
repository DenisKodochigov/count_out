package com.example.count_out.ui.view_components.drag_drop_column.column

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

data class StateDDColumn( var sizeList: Int = 0, ) {

    private val offsetItems: MutableState<Map<Int, Int>> = mutableStateOf(emptyMap())
    private val offsetY: MutableState<Float> = mutableFloatStateOf(0f)
    private val indexDD: MutableState<Int> = mutableIntStateOf(-1)
    private val indexMoved: MutableState<Int> = mutableIntStateOf(-1)
    val heightItem: MutableState<Int> = mutableIntStateOf(0)
    val heightList: MutableState<Int> = mutableIntStateOf(0)

    private var offsetStart: Int = 0
    fun init(){
        offsetY.value =0f
        offsetStart = 0
        initMap()
    }

    fun onStartDrag(offset: Float) {
        offsetStart = offset.toInt()
        offsetY.value = offset
        indexDD.value = (offset / heightItem.value).toInt()
        indexMoved.value = indexDD.value
    }
    fun onDrag(delta: Float){
        offsetY.value += delta
        if (offsetY.value in 0f..(heightList.value + heightItem.value/2f)) {
            val koef = if (offsetY.value > heightItem.value * (indexMoved.value + 1.5)) { 1 }
                        else if (offsetY.value < heightItem.value * (indexMoved.value - 0.5) ) { -1 }
                        else 0
            offsetItems.value = addOffset( koef )
        }
    }
    fun onDragEnd( onMoveItem: (Int, Int) -> Unit){
        onMoveItem(indexDD.value, indexMoved.value) }
    fun onDragCancel(){ indexDD.value = -1 }
    fun itemZ(index: Int) = if (index == indexDD.value) 1f else 0f
    fun offsetIt(index: Int) = (offsetItems.value[index] ?: 0) +
        if (index == indexDD.value) { offsetY.value.toInt() - offsetStart } else 0

    private fun initMap() {
        val mapOffset: MutableMap<Int, Int> = mutableMapOf()
        for ( i in 0..< sizeList) mapOffset[i] = 0
        offsetItems.value = mapOffset.toMap()
    }

    private fun addOffset( koef: Int ): Map<Int, Int>{
        if ((indexMoved.value + koef) in 0..< sizeList) indexMoved.value += koef
//        lg("indexMoved ${indexMoved.value}  indexDD ${indexDD.value}  koef $koef   size $sizeList")
        val map = offsetItems.value.toMutableMap()
        map[indexMoved.value] = (map[indexMoved.value] ?: 0) + (-1 * koef) * heightItem.value
        return map
    }
}
