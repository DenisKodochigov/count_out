package com.example.count_out.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.count_out.R

val interBold = TextStyle(fontFamily = FontFamily(Font(R.font.inter_bold)))
val interBold12 = interBold.copy(fontSize = 12.sp)
val interBold14 = interBold.copy(fontSize = 14.sp)
val interBold16 = interBold.copy(fontSize = 16.sp)
val interBold48 = interBold.copy(fontSize = 48.sp)

val interExtraLight = TextStyle(fontFamily = FontFamily(Font(R.font.inter_extralight)))
val interExtraLight12 = interExtraLight.copy(fontSize = 12.sp)

val interLight = TextStyle(fontFamily = FontFamily(Font(R.font.inter_light)))
val interLight12 = interLight.copy(fontSize = 12.sp)
val interLight12Start = interLight.copy(fontSize = 12.sp, textAlign = TextAlign.Start, textDecoration = TextDecoration.None)
val interLight16 = interLight.copy(fontSize = 16.sp)

val interThin = TextStyle(fontFamily = FontFamily(Font(R.font.inter_thin)))
val interThin10Start = interThin.copy(fontSize = 10.sp, textAlign = TextAlign.Start)
val interThin12 = interThin.copy(fontSize = 12.sp)
val interThin14 = interThin.copy(fontSize = 14.sp)

val interReg = TextStyle(fontFamily = FontFamily(Font(R.font.inter_regular)))
val interReg10Start = interReg.copy(fontSize = 10.sp, textAlign = TextAlign.Start)
val interReg12 = interReg.copy(fontSize = 12.sp)
val interReg14 = interReg.copy(fontSize = 14.sp)
val interReg16 = interReg.copy(fontSize = 16.sp)

val alumnisans = TextStyle(fontFamily = FontFamily(Font(R.font.alumnisans_regular)))
val alumniReg12 = alumnisans.copy(fontSize = 12.sp)
val alumniReg14 = alumnisans.copy(fontSize = 14.sp)

val typography = Typography()
//    displayLarge = interBold48,
//    bodyMedium = interReg12,
//)
//displayMedium = COMPILED_CODE,
//displaySmall = COMPILED_CODE,
//headlineLarge = COMPILED_CODE,
//headlineMedium = COMPILED_CODE,
//headlineSmall = COMPILED_CODE,
//titleLarge = COMPILED_CODE,
//titleMedium = COMPILED_CODE,
//titleSmall = COMPILED_CODE,
//bodyLarge = COMPILED_CODE,
//bodyMedium = COMPILED_CODE,
//bodySmall = COMPILED_CODE,
//labelLarge = COMPILED_CODE,
//labelMedium = COMPILED_CODE,
//labelSmall = COMPILED_CODE