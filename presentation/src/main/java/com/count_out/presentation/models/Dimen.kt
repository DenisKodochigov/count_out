package com.count_out.presentation.models

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.count_out.presentation.R

val alumBodySmall = Typography().bodySmall.copy(fontFamily = FontFamily(Font(R.font.alumnisans_regular)))
val alumBodyMedium = Typography().bodyMedium.copy(fontFamily = FontFamily(Font(R.font.alumnisans_regular)))
val alumBodyLarge = Typography().bodyLarge.copy(fontFamily = FontFamily(Font(R.font.alumnisans_regular)))

object Dimen {

    val width6 = 6.dp
    val paddingAppHor = 8.dp
//    val paddingAppVer = 12.dp

    val bsPaddingHor1 = 12.dp
    val bsSpacerHeight = 14.dp
//    val bsSpacerHeight1 = 8.dp
    val bsItemPaddingHor = 6.dp

    val bsConfirmationButtonTopHeight = 30.dp
    val bsSpacerBottomHeight = 50.dp
    val bsHeightWindowsListBle = 350.dp
    val bsItemPaddingVer = 4.dp

    val width8 = 8.dp
    val width4 = 4.dp
    val height4 = 4.dp
//    val height6 = 6.dp
//    val height8 = 8.dp
    private val dp0 = 0.dp
    private val dp1 = 1.dp
    private val dp2 = 2.dp
    val contourAll1 = PaddingValues(top = dp1, bottom = dp1, start = dp1, end = dp1)
    val contourBot1 = PaddingValues(top = dp0, bottom = dp1, start = dp0, end = dp0)
    val contourAll2 = PaddingValues(top = dp2, bottom = dp2, start = dp2, end = dp2)
    val contourHor2 = PaddingValues(top = dp2, bottom = dp2, start = dp0, end = dp0)

    val sizeIcon = 32.dp
    val sizeIconLarge = 44.dp
    val sizeBetweenIcon = 16.dp
    const val TAB_FADE_IN_ANIMATION_DURATION = 150
    const val TAB_FADE_IN_ANIMATION_DELAY = 100
    const val TAB_FADE_OUT_ANIMATION_DURATION = 100

    // Основные цвета:
    val Black = Color(0xFF000000)
    val Gray = Color(0xFF808080)
    val Silver = Color(0xFFC0C0C0)
    val White = Color(0xFFFFFFFF)
    val Fuchsia = Color(0xFFFF00FF)
    val Purple = Color(0xFF800080)
    val Red = Color(0xFFFF0000)
    val Maroon = Color(0xFF800000)
    val Yellow = Color(0xFFFFFF00)
    val Olive = Color(0xFF808000)
    val Lime = Color(0xFF00FF00)
    val Green = Color(0xFF008000)
    val Aqua = Color(0xFF00FFFF)
    val Teal = Color(0xFF008080)
    val Blue = Color(0xFF0000FF)
    val Navy = Color(0xFF000080)
    //Красные тона
    val IndianRed = Color(0xFFCD5C5C)
    val LightCoral = Color(0xFFF08080)
    val Salmon = Color(0xFFFA8072)
    val DarkSalmon = Color(0xFFE9967A)
    val LightSalmon1 = Color(0xFFFFA07A)
    val Crimson = Color(0xFFDC143C)
    val Red1 = Color(0xFFFF0000)
    val FireBrick = Color(0xFFB22222)
    val DarkRed = Color(0xFF8B0000)
    // Розовые тона:
//    val Pink = Color(0xFFFFC0CB)
//    val LightPink = Color(0xFFFFB6C1)
//    val HotPink = Color(0xFFFF69B4)
//    val DeepPink = Color(0xFFFF1493)
//    val MediumVioletRed = Color(0xFFC71585)
//    val PaleVioletRed = Color(0xFFDB7093)
    // Оранжевые тона:
    val LightSalmon = Color(0xFFFFA07A)
    val Coral = Color(0xFFFF7F50)
    val Tomato = Color(0xFFFF6347)
    val OrangeRed = Color(0xFFFF4500)
    val DarkOrange = Color(0xFFFF8C00)
    val Orange = Color(0xFFFFA500)
    // Жёлтые тона:
    val Gold = Color(0xFFFFD700)
    val Yellow1 = Color(0xFFFFFF00)
    val LightYellow = Color(0xFFFFFFE0)
    val LemonChiffon = Color(0xFFFFFACD)
    val LightGoldenrodYellow = Color(0xFFFAFAD2)
    val PapayaWhip = Color(0xFFFFEFD5)
    val Moccasin = Color(0xFFFFE4B5)
    val PeachPuff = Color(0xFFFFDAB9)
    val PaleGoldenrod = Color(0xFFEEE8AA)
    val Khaki = Color(0xFFF0E68C)
    val DarkKhaki = Color(0xFFBDB76B)
    // Фиолетовые тона:
    val Lavender = Color(0xFFE6E6FA)
    val Thistle = Color(0xFFD8BFD8)
    val Plum = Color(0xFFDDA0DD)
    val Violet = Color(0xFFEE82EE)
    val Orchid = Color(0xFFDA70D6)
    val Fuchsia1 = Color(0xFFFF00FF)
    val Magenta = Color(0xFFFF00FF)
    val MediumOrchid = Color(0xFFBA55D3)
    val MediumPurple = Color(0xFF9370DB)
    val BlueViolet = Color(0xFF8A2BE2)
    val DarkViolet = Color(0xFF9400D3)
    val DarkOrchid = Color(0xFF9932CC)
    val DarkMagenta = Color(0xFF8B008B)
    val Purple1 = Color(0xFF800080)
    val Indigo = Color(0xFF4B0082)
    val SlateBlue = Color(0xFF6A5ACD)
    val DarkSlateBlue = Color(0xFF483D8B)
    // Коричневые тона:
    val Cornsilk = Color(0xFFFFF8DC)
    val BlanchedAlmond = Color(0xFFFFEBCD)
    val Bisque = Color(0xFFFFE4C4)
    val NavajoWhite = Color(0xFFFFDEAD)
    val Wheat = Color(0xFFF5DEB3)
    val BurlyWood = Color(0xFFDEB887)
    val Tan = Color(0xFFD2B48C)
    val RosyBrown = Color(0xFFBC8F8F)
    val SandyBrown = Color(0xFFF4A460)
    val Goldenrod = Color(0xFFDAA520)
    val DarkGoldenRod = Color(0xFFB8860B)
    val Peru = Color(0xFFCD853F)
    val Chocolate = Color(0xFFD2691E)
    val SaddleBrown = Color(0xFF8B4513)
    val Sienna = Color(0xFFA0522D)
    val Brown = Color(0xFFA52A2A)
    val Maroon1 = Color(0xFF800000)

    // Зелёные тона:
    val GreenYellow = Color(0xFFADFF2F)
    val Chartreuse = Color(0xFF7FFF00)
    val LawnGreen = Color(0xFF7CFC00)
    val Lime1 = Color(0xFF00FF00)
    val LimeGreen = Color(0xFF32CD32)
    val PaleGreen = Color(0xFF98FB98)
    val LightGreen = Color(0xFF90EE90)
    val MediumSpringGreen = Color(0xFF00FA9A)
    val SpringGreen = Color(0xFF00FF7F)
    val MediumSeaGreen = Color(0xFF3CB371)
    val SeaGreen = Color(0xFF2E8B57)
    val ForestGreen = Color(0xFF228B22)
    val Green1 = Color(0xFF008000)
    val DarkGreen = Color(0xFF006400)
    val YellowGreen = Color(0xFF9ACD32)
    val OliveDrab = Color(0xFF6B8E23)
    val Olive1 = Color(0xFF808000)
    val DarkOliveGreen = Color(0xFF556B2F)
    val MediumAquamarine = Color(0xFF66CDAA)
    val DarkSeaGreen = Color(0xFF8FBC8F)
    val LightSeaGreen = Color(0xFF20B2AA)
    val DarkCyan = Color(0xFF008B8B)
    val Teal1 = Color(0xFF008080)
    // Синие тона:
    val Aqua1 = Color(0xFF00FFFF)
    val Cyan = Color(0xFF00FFFF)
    val LightCyan = Color(0xFFE0FFFF)
    val PaleTurquoise = Color(0xFFAFEEEE)
    val Aquamarine = Color(0xFF7FFFD4)
    val Turquoise = Color(0xFF40E0D0)
    val MediumTurquoise = Color(0xFF48D1CC)
    val DarkTurquoise = Color(0xFF00CED1)
    val CadetBlue = Color(0xFF5F9EA0)
    val SteelBlue = Color(0xFF4682B4)
    val LightSteelBlue = Color(0xFFB0C4DE)
    val PowderBlue = Color(0xFFB0E0E6)
    val LightBlue = Color(0xFFADD8E6)
    val SkyBlue = Color(0xFF87CEEB)
    val LightSkyBlue = Color(0xFF87CEFA)
    val DeepSkyBlue = Color(0xFF00BFFF)
    val DodgerBlue = Color(0xFF1E90FF)
    val CornflowerBlue = Color(0xFF6495ED)
    val MediumSlateBlue = Color(0xFF7B68EE)
    val RoyalBlue = Color(0xFF4169E1)
    val Blue1 = Color(0xFF0000FF)
    val MediumBlue = Color(0xFF0000CD)
    val DarkBlue = Color(0xFF00008B)
    val Navy1 = Color(0xFF000080)
    val MidnightBlue = Color(0xFF191970)
    // Белые тона:
    val White1 = Color(0xFFFFFFFF)
    val Snow = Color(0xFFFFFAFA)
    val Honeydew = Color(0xFFF0FFF0)
    val MintCream = Color(0xFFF5FFFA)
    val Azure = Color(0xFFF0FFFF)
    val AliceBlue = Color(0xFFF0F8FF)
    val GhostWhite = Color(0xFFF8F8FF)
    val WhiteSmoke = Color(0xFFF5F5F5)
    val Seashell = Color(0xFFFFF5EE)
    val Beige = Color(0xFFF5F5DC)
    val OldLace = Color(0xFFFDF5E6)
    val FloralWhite = Color(0xFFFFFAF0)
    val Ivory = Color(0xFFFFFFF0)
    val AntiqueWhite = Color(0xFFFAEBD7)
    val Linen = Color(0xFFFAF0E6)
    val LavenderBlush = Color(0xFFFFF0F5)
    val MistyRose = Color(0xFFFFE4E1)
    // Серые тона:
    val Gainsboro = Color(0xFFDCDCDC)
    val LightGrey = Color(0xFFD3D3D3)
    val LightGray = Color(0xFFD3D3D3)
    val Silver1 = Color(0xFFC0C0C0)
    val DarkGray = Color(0xFFA9A9A9)
    val DarkGrey = Color(0xFFA9A9A9)
    val Gray1 = Color(0xFF808080)
    val Grey = Color(0xFF808080)
    val DimGray = Color(0xFF696969)
    val DimGrey = Color(0xFF696969)
    val LightSlateGray = Color(0xFF778899)
    val LightSlateGrey = Color(0xFF778899)
    val SlateGray = Color(0xFF708090)
    val SlateGrey = Color(0xFF708090)
    val DarkSlateGray = Color(0xFF2F4F4F)
    val DarkSlateGrey = Color(0xFF2F4F4F)
    val Black1 = Color(0xFF000000)



    val massColor = listOf(
        // Основные цвета
        listOf(Black, Gray, Silver, White, Fuchsia, Purple, Red, Maroon, Yellow, Olive, Lime, Green, Aqua, Teal, Blue, Navy),
        // Розовые тона
        listOf(IndianRed, LightCoral, Salmon, DarkSalmon, LightSalmon1, Crimson, Red1, FireBrick, DarkRed),
        // Оранжевые тона
        listOf(LightSalmon, Coral, Tomato, OrangeRed, DarkOrange, Orange),
        // Жёлтые тона
        listOf(Gold, Yellow1, LightYellow, LemonChiffon, LightGoldenrodYellow, PapayaWhip, Moccasin, PeachPuff, PaleGoldenrod, Khaki, DarkKhaki),
        // Фиолетовые тона
        listOf(Lavender, Thistle, Plum, Violet, Orchid, Fuchsia1, Magenta, MediumOrchid, MediumPurple, BlueViolet, DarkViolet, DarkOrchid, DarkMagenta, Purple1, Indigo, SlateBlue, DarkSlateBlue),
        // Коричневые тона
        listOf(Cornsilk, BlanchedAlmond, Bisque, NavajoWhite, Wheat, BurlyWood, Tan, RosyBrown, SandyBrown, Goldenrod, DarkGoldenRod, Peru, Chocolate, SaddleBrown, Sienna, Brown, Maroon1),
        // Зелёные тона
        listOf(GreenYellow, Chartreuse, LawnGreen, Lime1, LimeGreen, PaleGreen, LightGreen, MediumSpringGreen, SpringGreen, MediumSeaGreen, SeaGreen, ForestGreen, Green1, DarkGreen, YellowGreen, OliveDrab, Olive1, DarkOliveGreen, MediumAquamarine, DarkSeaGreen, LightSeaGreen, DarkCyan, Teal1),
        // Синие тона
        listOf(Aqua1, Cyan, LightCyan, PaleTurquoise, Aquamarine, Turquoise, MediumTurquoise, DarkTurquoise, CadetBlue, SteelBlue, LightSteelBlue, PowderBlue, LightBlue, SkyBlue, LightSkyBlue, DeepSkyBlue, DodgerBlue, CornflowerBlue, MediumSlateBlue, RoyalBlue, Blue1, MediumBlue, DarkBlue, Navy1, MidnightBlue),
        // Белые тона
        listOf(White1, Snow, Honeydew, MintCream, Azure, AliceBlue, GhostWhite, WhiteSmoke, Seashell, Beige, OldLace, FloralWhite, Ivory, AntiqueWhite, Linen, LavenderBlush, MistyRose),
        // Серые тона
        listOf(Gainsboro, LightGrey, LightGray, Silver1, DarkGray, DarkGrey, Gray1, Grey, DimGray, DimGrey, LightSlateGray, LightSlateGrey, SlateGray, SlateGrey, DarkSlateGray, DarkSlateGrey, Black1),
    )
}