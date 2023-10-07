package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BulletListItem(
	modifier: Modifier = Modifier,
	text: String,
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.Top,
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Box(
			modifier = Modifier.padding(top = 4.dp).size(8.dp).clip(CircleShape)
				.background(MaterialTheme.colorScheme.onBackground),
		)
		Text(
			text = text,
			style = MaterialTheme.typography.titleLarge.copy(
					fontWeight = FontWeight.Normal,
					platformStyle = PlatformTextStyle(
						spanStyle = PlatformSpanStyle.Default,
						paragraphStyle = PlatformParagraphStyle.Default,
					)
				),
		)
	}
}
