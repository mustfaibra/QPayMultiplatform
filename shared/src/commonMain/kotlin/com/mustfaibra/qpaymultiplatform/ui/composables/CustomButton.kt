package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
	modifier: Modifier = Modifier,
	text: String,
	textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
	enabled: Boolean = true,
	containerColor: Color = MaterialTheme.colorScheme.primary,
	contentColor: Color = MaterialTheme.colorScheme.onPrimary,
	disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
	disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
	shape: Shape = MaterialTheme.shapes.medium,
	padding: PaddingValues = PaddingValues(
		horizontal = 24.dp,
		vertical = 12.dp,
	),
	leadingIcon: @Composable() (() -> Unit)? = null,
	trailingIcon: (@Composable () -> Unit)? = null,
	onClick: () -> Unit,
) {
	Button(
		modifier = modifier,
		enabled = enabled,
		colors = ButtonDefaults.buttonColors(
			containerColor = containerColor,
			contentColor = contentColor,
			disabledContainerColor = disabledContainerColor,
			disabledContentColor = disabledContentColor,
		),
		onClick = onClick,
		shape = shape,
		contentPadding = padding,
	) {
		leadingIcon?.invoke()
		Text(
			text = text,
			style = textStyle,
		)
		trailingIcon?.invoke()
	}
}
