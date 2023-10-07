package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun QPayIcon(
	painter: Painter,
	shape: Shape = CircleShape,
	background: Color = MaterialTheme.colorScheme.inversePrimary.copy(0.4f),
	tint: Color = Color.Unspecified,
	size: Dp = 24.dp,
	padding: PaddingValues = PaddingValues(12.dp),
	onClick: () -> Unit,
) {
	Icon(
		painter = painter,
		contentDescription = null,
		modifier = Modifier
			.clip(shape)
			.background(background)
			.clickable { onClick() }
			.padding(padding)
			.size(size),
		tint = tint,
	)
}
