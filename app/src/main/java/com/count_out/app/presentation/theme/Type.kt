package com.count_out.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val fontInter = FontFamily( Font(com.count_out.presentation.R.font.inter_regular, FontWeight.W300))
val fontAlum = FontFamily( Font(com.count_out.presentation.R.font.alumnisans_medium, FontWeight.W300))

@Composable fun myTypography() = Typography(
    displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = fontAlum),
    displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = fontAlum),
    displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = fontAlum),
    headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = fontAlum),
    headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = fontAlum),
    headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = fontAlum),
    titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = fontAlum),
    titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = fontAlum),
    titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = fontAlum),
    bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = fontAlum),
    bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = fontAlum),
    bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = fontAlum),
    labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = fontAlum),
    labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = fontAlum),
    labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = fontAlum),
)


//val myTypography = Typography()

//val interBold = TextStyle(fontFamily = FontFamily(Font(R.font.inter_bold)))
//val interBold16 = interBold.copy(fontSize = 16.sp)
//
//val interLight = TextStyle(fontFamily = FontFamily(Font(R.font.inter_light)))
//val interLight12 = interLight.copy(fontSize = 12.sp)
//val interLight14Start = interLight.copy(fontSize = 14.sp, textAlign = TextAlign.Start, textDecoration = TextDecoration.None)
//
//val interThin = TextStyle(fontFamily = FontFamily(Font(R.font.inter_thin)))
//val interThin12Start = interThin.copy(fontSize = 12.sp, textAlign = TextAlign.Start)
//
//val interReg = TextStyle(fontFamily = FontFamily(Font(R.font.inter_regular)))
//val interReg12 = interReg.copy(fontSize = 12.sp)
//val interReg14 = interReg.copy(fontSize = 14.sp)
//
//val alumBodySmall = Typography().bodySmall.copy(fontFamily = FontFamily(Font(R.font.alumnisans_regular)))
//val alumBodyMedium = Typography().bodyMedium.copy(fontFamily = FontFamily(Font(R.font.alumnisans_regular)))
//val alumBodyLarge = Typography().bodyLarge.copy(fontFamily = FontFamily(Font(R.font.alumnisans_regular)))


//    headlineLarge = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 32.sp,
//        lineHeight = 40.sp,
//        letterSpacing = 0.sp
//    ),
//    headlineMedium = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 28.sp,
//        lineHeight = 36.sp,
//        letterSpacing = 0.sp
//    ),
//    headlineSmall = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 24.sp,
//        lineHeight = 32.sp,
//        letterSpacing = 0.sp
//    ),
//    titleLarge = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    titleMedium = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.15.sp
//    ),
//    titleSmall = TextStyle(
//        fontWeight = FontWeight.Bold,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp
//    ),
//    bodyLarge = TextStyle(
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.15.sp
//    ),
//    bodyMedium = TextStyle(
//        fontWeight = FontWeight.Medium,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.25.sp
//    ),
//    bodySmall = TextStyle(
//        fontWeight = FontWeight.Bold,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.4.sp
//    ),
//    labelLarge = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 14.sp,
//        lineHeight = 20.sp,
//        letterSpacing = 0.1.sp
//    ),
//    labelMedium = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 12.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    ),
//    labelSmall = TextStyle(
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )