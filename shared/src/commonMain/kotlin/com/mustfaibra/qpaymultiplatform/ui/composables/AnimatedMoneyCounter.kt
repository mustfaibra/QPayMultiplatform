package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.utils.get

@Composable
fun AnimatedMoneyCounter(
	target: Double,
	textStyle: TextStyle = MaterialTheme.typography.titleLarge
		.copy(fontWeight = FontWeight.SemiBold),
) {
	var numberState by remember {
		mutableStateOf(target.times(0.9f).toFloat())
	}
	val animatedNumber by animateFloatAsState(
		targetValue = numberState,
		animationSpec = TweenSpec(
			durationMillis = 4000,
			easing = LinearEasing,
		)
	)
	Text(
		text = SharedRes.strings.sar_x.get(animatedNumber),
		style = textStyle,
	)
	SideEffect {
		numberState = target.toFloat()
	}
}
