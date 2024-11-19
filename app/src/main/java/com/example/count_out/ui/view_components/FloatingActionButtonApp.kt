package com.example.count_out.ui.view_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.count_out.ui.theme.alumBodyMedium

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
        text = { TextApp(text = stringResource(id = textId), style = alumBodyMedium) },
    )
}
@Composable fun IconFab(icon: ImageVector){
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
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

