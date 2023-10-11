package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun QPayOutlineButton(
	modifier: Modifier = Modifier,
	text: String,
	textStyle: TextStyle = MaterialTheme.typography.titleSmall
		.copy(fontWeight = FontWeight.SemiBold),
	icon: Painter? = null,
	contentColor: Color = MaterialTheme.colorScheme.primary,
	onClick: () -> Unit,
) {
	OutlinedButton(
		modifier = modifier
			.shadow(
				elevation = 8.dp,
				shape = MaterialTheme.shapes.small,
				spotColor = MaterialTheme.colorScheme.primary,
			),
		onClick = onClick,
		shape = MaterialTheme.shapes.small,
		colors = ButtonDefaults.buttonColors(
			containerColor = MaterialTheme.colorScheme.surface,
			contentColor = MaterialTheme.colorScheme.primary,
		),
		border = BorderStroke(
			width = Dp.Hairline, brush = Brush.horizontalGradient(
				colors = listOf(
					MaterialTheme.colorScheme.primary,
					MaterialTheme.colorScheme.inversePrimary,
				)
			)
		),
		contentPadding = PaddingValues(
			horizontal = 24.dp,
			vertical = 8.dp,
		)
	) {
		icon?.let {
			Icon(
				painter = icon,
				contentDescription = null,
				modifier = Modifier
					.padding(end = 8.dp)
					.clip(MaterialTheme.shapes.medium)
					.background(contentColor)
					.padding(2.dp)
					.size(16.dp),
				tint = contentColorFor(contentColor),
			)
		}
		Text(
			text = text,
			style = textStyle,
			color = contentColor
		)
	}
}
