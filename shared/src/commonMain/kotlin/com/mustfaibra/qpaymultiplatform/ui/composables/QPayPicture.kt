package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
fun QPayPicture(
	modifier: Modifier = Modifier,
	image: Painter,
	size: Dp = 36.dp,
	shape: Shape = CircleShape,
	background: Color = MaterialTheme.colorScheme.inversePrimary.copy(0.4f),
	onImageClicked: () -> Unit,
) {
	Image(
		painter = image,
		contentDescription = null,
		modifier = modifier
			.size(size = size)
			.clip(shape)
			.background(background)
			.clickable { onImageClicked() },
	)
//	when (image) {
//		is Int -> {
//			Image(
//				painter = painterResource(image),
//				contentDescription = null,
//				modifier = modifier
//					.size(size = size)
//					.clip(shape)
//					.background(background)
//					.clickable { onImageClicked() }
//			)
//		}
//
//		is Painter -> {
//			Image(
//				painter = image,
//				contentDescription = null,
//				modifier = modifier
//					.size(size = size)
//					.clip(shape)
//					.background(background)
//					.clickable { onImageClicked() }
//			)
//		}
//
//		else -> {
//
//		}
//	}
}
