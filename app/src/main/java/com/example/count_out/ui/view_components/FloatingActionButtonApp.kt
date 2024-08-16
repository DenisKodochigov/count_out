package com.example.count_out.ui.view_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.theme.alumniReg14
import com.example.count_out.ui.theme.colors3
import com.example.count_out.ui.theme.interLight12
import java.math.RoundingMode

@Composable fun ExtendedFAB(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Add,
    onClick: () -> Unit,
    textId: Int
){
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        icon = { IconFab(icon = icon) },
        text = { TextApp(text = stringResource(id = textId), style = alumniReg14) },
    )
}
@Composable fun IconFab(icon: ImageVector){
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = colors3.onPrimaryContainer,
    )
}

//            FABCorrectInterval(
//                currentValue = playSet.intervalReps,
//                downInterval = {
//                    uiState.training?.let {
//                        uiState.updateSet( it.idTraining,
//                            (playSet as SetDB).copy(
//                                intervalReps = (playSet.intervalReps - 0.1).toPositive()))
//                    }
//                },
//                upInterval = {
//                    uiState.training?.let {
//                        uiState.updateSet(it.idTraining,
//                            (playSet as SetDB).copy( intervalReps = playSet.intervalReps + 0.1 ))
//                    }
//                }
//            )
//        }

