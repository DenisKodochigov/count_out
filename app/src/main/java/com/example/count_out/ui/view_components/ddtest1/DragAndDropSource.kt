package com.example.count_out.ui.view_components.ddtest1

import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable fun DragAndDropSource(){

    Text(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(Color.Yellow)
            .dragAndDropSource {
                detectTapGestures(
                    onLongPress = {
                        startTransfer(
                            DragAndDropTransferData(
                                ClipData(
                                    "",
                                    arrayOf("array of mimeTypes - those tell dragAndDropTargets which type of dragAndDropSource this is, such as an image, text, phone number, or your custom type"),
                                    ClipData.Item("you can add new clip items with data here")
                                )
                            )
                        )
                    }
                )
            },
        color = Color.Black,
        textAlign = TextAlign.Center,
        text = "Hold me to drag and drop"
    )
    val enteredIndex = remember { mutableIntStateOf(-1) }

    LazyColumn {
        items(50) {
            Text(
                modifier = Modifier
                    .then(
                        if (enteredIndex.intValue == it) { Modifier.background(Color.White)
                        } else Modifier.background(Color.Cyan)
                    )
                    .fillMaxWidth()
                    .height(80.dp)
                    .dragAndDropTarget(
                        shouldStartDragAndDrop = { true },
                        target = object : DragAndDropTarget {
                            override fun onDrop(event: DragAndDropEvent): Boolean {
                                enteredIndex.intValue = -1
                                return true
                            }
                            override fun onEntered(event: DragAndDropEvent) {
                                super.onEntered(event)
                                enteredIndex.intValue = it
                            }
                            override fun onExited(event: DragAndDropEvent) {
                                super.onExited(event)
                                enteredIndex.intValue = -1
                            }
                        }
                    ),
                color = Color.Black,
                textAlign = TextAlign.Center,
                text = "Content $it, that responds to drag and drop"
            )
        }
    }
}