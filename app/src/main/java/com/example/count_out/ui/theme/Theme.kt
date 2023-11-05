package com.example.count_out.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import com.example.count_out.AppBase
import com.example.count_out.R
import com.example.count_out.entity.SizeElement
import com.example.count_out.entity.TypeText
import com.example.count_out.navigation.ScreenDestination

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable fun styleApp(nameStyle: TypeText): TextStyle {

    return when( nameStyle ){
        TypeText.NAME_SCREEN -> when (AppBase.scale){
            1-> MaterialTheme.typography.headlineSmall
            2 -> MaterialTheme.typography.titleMedium
            else -> MaterialTheme.typography.headlineMedium //0
        }
        TypeText.NAME_SECTION -> when (AppBase.scale){
            1-> MaterialTheme.typography.titleLarge
            2 -> MaterialTheme.typography.titleSmall
            else -> MaterialTheme.typography.headlineSmall //0
        }
        TypeText.TEXT_IN_LIST -> when (AppBase.scale){
            1-> MaterialTheme.typography.titleLarge
            2 -> MaterialTheme.typography.titleSmall
            else -> MaterialTheme.typography.headlineSmall //0
        }
        TypeText.TEXT_IN_LIST_SMALL -> when (AppBase.scale){
            1-> MaterialTheme.typography.labelLarge
            2 -> MaterialTheme.typography.labelMedium
            else -> MaterialTheme.typography.bodyMedium //0
        }
        TypeText.EDIT_TEXT -> when (AppBase.scale){
            1-> MaterialTheme.typography.titleLarge
            2 -> MaterialTheme.typography.titleSmall
            else -> MaterialTheme.typography.headlineSmall //0
        }
        TypeText.EDIT_TEXT_TITLE -> when (AppBase.scale){
            1-> MaterialTheme.typography.labelLarge
            2 -> MaterialTheme.typography.labelMedium
            else -> MaterialTheme.typography.bodyMedium //0
        }
        TypeText.TEXT_IN_LIST_SETTING -> when (AppBase.scale){
            1-> MaterialTheme.typography.titleLarge
            2 -> MaterialTheme.typography.titleSmall
            else -> MaterialTheme.typography.headlineSmall //0
        }
        TypeText.NAME_SLIDER -> when (AppBase.scale){
            1-> MaterialTheme.typography.labelLarge
            2 -> MaterialTheme.typography.labelMedium
            else -> MaterialTheme.typography.bodyMedium //0
        }
    }
}

@Composable fun sizeApp(sizeElement: SizeElement): Dp {

    return when( sizeElement ){
        SizeElement.SIZE_FAB -> when (AppBase.scale){
            1-> dimensionResource(R.dimen.size_fab_medium)
            2 -> dimensionResource(R.dimen.size_fab_small)
            else -> dimensionResource(R.dimen.size_fab_large)
        }
        SizeElement.HEIGHT_BOTTOM_BAR -> when (AppBase.scale){
            1-> dimensionResource(R.dimen.height_bottom_bar_medium)
            2 -> dimensionResource(R.dimen.height_bottom_bar_small)
            else -> dimensionResource(R.dimen.height_bottom_bar_large)
        }
        SizeElement.PADDING_FAB -> when (AppBase.scale){
            1-> dimensionResource(R.dimen.padding_fab_medium)
            2 -> dimensionResource(R.dimen.padding_fab_small)
            else -> dimensionResource(R.dimen.padding_fab_large)
        }
        SizeElement.OFFSET_FAB -> when (AppBase.scale){
            1-> dimensionResource(R.dimen.offset_fab_medium)
            2 -> dimensionResource(R.dimen.offset_fab_small)
            else -> dimensionResource(R.dimen.offset_fab_large)
        }
        SizeElement.HEIGHT_FAB_BOX -> when (AppBase.scale){
            1-> dimensionResource(R.dimen.height_fab_box_medium)
            2 -> dimensionResource(R.dimen.height_fab_box_small)
            else -> dimensionResource(R.dimen.height_fab_box_large)
        }
    }
}
@Composable fun getIdImage(screen: ScreenDestination):Int{
    val dayNight = isSystemInDarkTheme()
    return if (dayNight) screen.pictureDay else screen.pictureNight
}