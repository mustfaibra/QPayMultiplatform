package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun SignInWithButton(
	modifier: Modifier = Modifier,
	text: String,
	optionIcon: ImageResource,
	textStyle: TextStyle = TextStyle(
		fontWeight = FontWeight.Medium,
		fontSize = 16.sp,
	),
	enabled: Boolean = true,
	containerColor: Color,
	contentColor: Color,
	disabledContainerColor: Color = Color.LightGray,
	disabledContentColor: Color = Color.Gray,
	shape: Shape = MaterialTheme.shapes.medium,
	iconTint: Color = Color.Unspecified,
	padding: PaddingValues = PaddingValues(
		horizontal = 24.dp,
		vertical = 12.dp
	),
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
		Icon(
			modifier = Modifier
				.padding(end = 20.dp)
				.size(24.dp),
			painter = painterResource(optionIcon),
			contentDescription = null,
			tint = iconTint,
		)
		Text(
			text = text,
			style = textStyle,
		)
	}
}
