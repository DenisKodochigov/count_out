package com.example.count_out.ui.view_components.drag_drop_column

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.count_out.ui.theme.Purple
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.sign

data class ShoesArticle(
    var id: Int = 0,
    var title: String = "",
    var price: Float = 0f,
    var width: String = "",
    var drawable: Int = 0,
    var color: Color = Color.Transparent
) {
    companion object {
        var ID = 0
    }
}
data class Particle(
    val color: Color,
    val x: Int,
    val y: Int,
    val radius: Float
)
private val particlesStreamRadii = mutableListOf<Float>()
private var itemHeight = 0
private var particleRadius = 0f
private var slotItemDifference = 0f
enum class SlideState { NONE, UP, DOWN }
val allShoesArticles = arrayOf(
    ShoesArticle(title = "Nike Air Max 270", price = 199.8f, width = "2X Wide", drawable = 1, color = Red),
    ShoesArticle(title = "Nike Joyride Run V", price = 249.1f, width = "3X Wide", drawable = 2, color = Blue),
    ShoesArticle(title = "Nike Space Hippie 04", price = 179.7f, width = "Extra Wide", drawable = 3, color = Purple)
)

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun Home() {
    val shoesArticles = remember { mutableStateListOf(*allShoesArticles) }
    val slideStates = remember { mutableStateMapOf<ShoesArticle, SlideState>().apply {
        shoesArticles.map { shoesArticle ->
            shoesArticle to SlideState.NONE }.toMap().also { putAll(it) }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "List Animations In Compose") },
                actions = {
                    IconButton(onClick = {
                        val newShoesArticles = mutableListOf<ShoesArticle>()
                        ShoesArticle.ID += 1
                        allShoesArticles.forEach { newShoesArticles.add(it.copy(id = ShoesArticle.ID)) }
                        shoesArticles.addAll(newShoesArticles)
                        Log.i("MainActivity", shoesArticles.toList().toString())
                    }) { Icon(Icons.Filled.AddCircle, contentDescription = null) }
                },
            )
        }
    ) { innerPadding ->
        ShoesList(
            modifier = Modifier.padding(innerPadding),
            shoesArticles = shoesArticles,
            slideStates = slideStates,
            updateSlidedState = { shoesArticle, slideState -> slideStates[shoesArticle] = slideState },
            updateItemPosition = { currentIndex, destinationIndex ->
                val shoesArticle = shoesArticles[currentIndex]
                shoesArticles.removeAt(currentIndex)
                shoesArticles.add(destinationIndex, shoesArticle)
                slideStates.apply {
                    shoesArticles.map { shoesArticle -> shoesArticle to SlideState.NONE }.toMap().also { putAll(it) }
                }
            }
        )
    }
}

//##############################################################################################################
fun Modifier.dragToReorder(
    shoesArticle: ShoesArticle,
    shoesArticles: MutableList<ShoesArticle>,
    itemHeight: Int,
    updateSlideState: (shoesArticle: ShoesArticle, slideState: SlideState) -> Unit,
    isDraggedAfterLongPress: Boolean,
    onStartDrag: () -> Unit,
    onStopDrag: (currentIndex: Int, destinationIndex: Int) -> Unit,
): Modifier = composed {
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    pointerInput(Unit) {
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            val shoesArticleIndex = shoesArticles.indexOf(shoesArticle)
            val offsetToSlide = itemHeight / 4
            var numberOfItems = 0
            var previousNumberOfItems: Int
            var listOffset = 0

            val onDragStart = {
                // Interrupt any ongoing animation of other items.
                launch {
                    offsetX.stop()
                    offsetY.stop()
                }
                onStartDrag()
            }
            val onDrag = {change: PointerInputChange ->
                val horizontalDragOffset = offsetX.value + change.positionChange().x
                launch { offsetX.snapTo(horizontalDragOffset) }
                val verticalDragOffset = offsetY.value + change.positionChange().y
                launch {
                    offsetY.snapTo(verticalDragOffset)
                    val offsetSign = offsetY.value.sign.toInt()
                    previousNumberOfItems = numberOfItems
                    numberOfItems = calculateNumberOfSlidItems(
                        offsetY.value * offsetSign,
                        itemHeight,
                        offsetToSlide,
                        previousNumberOfItems
                    )

                    if (previousNumberOfItems > numberOfItems) {
                        updateSlideState(
                            shoesArticles[shoesArticleIndex + previousNumberOfItems * offsetSign], SlideState.NONE)
                    } else if (numberOfItems != 0) {
                        try {
                            updateSlideState(
                                shoesArticles[shoesArticleIndex + numberOfItems * offsetSign],
                                if (offsetSign == 1) SlideState.UP else SlideState.DOWN
                            )
                        } catch (e: IndexOutOfBoundsException) {
                            numberOfItems = previousNumberOfItems
                            Log.i("DragToReorder", "Item is outside or at the edge")
                        }
                    }
                    listOffset = numberOfItems * offsetSign
                }
                // Consume the gesture event, not passed to external
                change.consumePositionChange()
            }
            val onDragEnd = {
                launch { offsetX.animateTo(0f) }
                launch {
                    offsetY.animateTo(itemHeight * numberOfItems * offsetY.value.sign)
                    onStopDrag(shoesArticleIndex, shoesArticleIndex + listOffset)
                }
            }
            if (isDraggedAfterLongPress)
                detectDragGesturesAfterLongPress(
                    onDragStart = { onDragStart() },
                    onDrag = { change, _ -> onDrag(change) },
                    onDragEnd = { onDragEnd() } )
//            else
//                while (true) {
//                    val pointerId = awaitPointerEventScope { awaitFirstDown().id }
//                    awaitPointerEventScope {
//                        drag(pointerId) { change ->
//                            onDragStart()
//                            onDrag(change)
//                        }
//                    }
//                    onDragEnd()
//                }
        }
    }
    .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
}

private fun calculateNumberOfSlidItems(offsetY: Float, itemHeight: Int, offsetToSlide: Int, previousNumberOfItems: Int
): Int {
    val numberOfItemsInOffset = (offsetY / itemHeight).toInt()
    val numberOfItemsPlusOffset = ((offsetY + offsetToSlide) / itemHeight).toInt()
    val numberOfItemsMinusOffset = ((offsetY - offsetToSlide - 1) / itemHeight).toInt()
    return when {
        ((offsetY - offsetToSlide - 1) < 0) -> 0
        numberOfItemsPlusOffset > numberOfItemsInOffset -> numberOfItemsPlusOffset
        numberOfItemsMinusOffset < numberOfItemsInOffset -> numberOfItemsInOffset
        else -> previousNumberOfItems
    }
}

@ExperimentalAnimationApi
@Composable
fun ShoesList(
    modifier: Modifier,
    shoesArticles: MutableList<ShoesArticle>,
    slideStates: Map<ShoesArticle, SlideState>,
    updateSlidedState: (shoesArticle: ShoesArticle, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier.padding(top = 12.dp)
    ) {
        items(shoesArticles.size) { index ->
            val shoesArticle = shoesArticles.getOrNull(index)
            if (shoesArticle != null) {
                key(shoesArticle) {
                    val slideState = slideStates[shoesArticle] ?: SlideState.NONE
                    ShoesCard(
                        shoesArticle = shoesArticle,
                        slideState = slideState,
                        shoesArticles = shoesArticles,
                        updateSlideState = updateSlidedState,
                        updateItemPosition = updateItemPosition
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ShoesCard(shoesArticle: ShoesArticle, slideState: SlideState,
              shoesArticles: MutableList<ShoesArticle>,
              updateSlideState: (shoesArticle: ShoesArticle, slideState: SlideState) -> Unit,
              updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val itemHeightDp = 100.dp
    with(LocalDensity.current) {
        itemHeight = itemHeightDp.toPx().toInt()
        particleRadius = 3.dp.toPx()
        if (particlesStreamRadii.isEmpty())
            particlesStreamRadii.addAll(arrayOf(6.dp.toPx(), 10.dp.toPx(), 14.dp.toPx()))
        slotItemDifference = 18.dp.toPx()
    }
    val verticalTranslation by animateIntAsState(
        targetValue = when (slideState) {
            SlideState.UP -> -itemHeight
            SlideState.DOWN -> itemHeight
            else -> 0
        },
        label = "",
    )
    val isDragged = remember { mutableStateOf(false) }
    val zIndex = if (isDragged.value) 1.0f else 0.0f
    val rotation = if (isDragged.value) -5.0f else 0.0f
    val elevation = if (isDragged.value) 8.dp else 0.dp

    val currentIndex = remember { mutableIntStateOf(0) }
    val destinationIndex = remember { mutableIntStateOf(0) }

    val isPlaced = remember { mutableStateOf(false) }
    LaunchedEffect(isPlaced.value) {
        if (isPlaced.value) {
            if (currentIndex.intValue != destinationIndex.intValue) { updateItemPosition(currentIndex.intValue, destinationIndex.intValue) }
            isPlaced.value = false
        }
    }
    Box(
        Modifier
            .padding(horizontal = 16.dp)
            .offset { IntOffset(0, verticalTranslation) }
            .zIndex(zIndex)
            .rotate(rotation)
            .dragToReorder(
                shoesArticle = shoesArticle,
                shoesArticles = shoesArticles,
                itemHeight = itemHeight,
                updateSlideState = updateSlideState,
                isDraggedAfterLongPress = true,
                onStartDrag = { isDragged.value = true },
                onStopDrag = { cIndex, dIndex ->
                    isDragged.value = false
                    isPlaced.value = true
                    currentIndex.intValue = cIndex
                    destinationIndex.intValue = dIndex
                }
            )
    ) {
        Column(
            modifier = Modifier
                .shadow(elevation, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(color = shoesArticle.color)
                .padding(12.dp)
                .align(alignment = Alignment.Center)
                .fillMaxWidth()
        ) {
            Text(shoesArticle.title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
            Spacer(Modifier.height(4.dp))
            Text(text = "$ ${shoesArticle.price}", fontSize = 14.sp, color = Color.White)
        }
        Image(
            modifier = Modifier.align(Alignment.CenterEnd).size(itemHeightDp),
            painter = painterResource(id = shoesArticle.drawable),
            contentDescription = ""
        )
    }

}