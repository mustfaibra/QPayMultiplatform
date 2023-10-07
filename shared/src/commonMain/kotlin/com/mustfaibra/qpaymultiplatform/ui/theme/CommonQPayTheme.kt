package com.mustfaibra.qpaymultiplatform.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mustfaibra.qpaymultiplatform.resources.LocalStringProvider
import com.mustfaibra.qpaymultiplatform.resources.StringsProvider

val PrimaryBlue = Color(0xFF2b66d8)
val DarkBlue = Color(0xFF153779)
val SecondaryOrange = Color(0xFFFF9800)
val DarkOrange = Color(0xFF975B04)
val White = Color(0xFFFFFFFF)
val White200 = Color(0xFFF3F3F3)
val Black = Color(0xFF131313)
val Gray = Color(0xFF555555)

private val darkColorScheme = darkColorScheme(
	primary = PrimaryBlue,
	inversePrimary = DarkBlue,
	secondary = SecondaryOrange,
	tertiary = Gray,
	background = White200,
	surface = White,
	onPrimary = White200,
	onSecondary = White200,
	onTertiary = Black,
	onBackground = Black,
	onSurface = Black,
	onSurfaceVariant = Gray,
)

private val lightColorScheme = lightColorScheme(
	primary = PrimaryBlue,
	inversePrimary = DarkBlue,
	secondary = SecondaryOrange,
	tertiary = Gray,
	background = White200,
	surface = White,
	onPrimary = White200,
	onSecondary = White200,
	onTertiary = Black,
	onBackground = Black,
	onSurface = Black,
	onSurfaceVariant = Gray,
	error = Color.Red,
	onError = White200,
)

@Composable
fun CommonQPayTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	val colors = if (darkTheme) {
		darkColorScheme
	}
	else {
		lightColorScheme
	}
	
	val typography = Typography(
		displayLarge = TextStyle(
			fontWeight = FontWeight.Black,
			fontSize = 36.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		displayMedium = TextStyle(
			fontWeight = FontWeight.Medium,
			fontSize = 34.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		displaySmall = TextStyle(
			fontWeight = FontWeight.Medium,
			fontSize = 31.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		headlineLarge = TextStyle(
			fontWeight = FontWeight.ExtraBold,
			fontSize = 28.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		headlineMedium = TextStyle(
			fontWeight = FontWeight.Bold,
			fontSize = 26.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		headlineSmall = TextStyle(
			fontWeight = FontWeight.Bold,
			fontSize = 24.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		bodyLarge = TextStyle(
			fontWeight = FontWeight.Bold,
			fontSize = 21.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		bodyMedium = TextStyle(
			fontWeight = FontWeight.Medium,
			fontSize = 19.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		bodySmall = TextStyle(
			fontWeight = FontWeight.Normal,
			fontSize = 17.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		titleLarge = TextStyle(
			fontWeight = FontWeight.Medium,
			fontSize = 15.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		titleMedium = TextStyle(
			fontWeight = FontWeight.Normal,
			fontSize = 14.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
		titleSmall = TextStyle(
			fontWeight = FontWeight.Light,
			fontSize = 12.sp,
			// lineHeight			lineHeight = 20.sp,  = 20.sp,
		),
	)
	
	MaterialTheme(
		colorScheme = colors,
		typography = typography,
		shapes = Shapes(),
		content = content,
	)
}
