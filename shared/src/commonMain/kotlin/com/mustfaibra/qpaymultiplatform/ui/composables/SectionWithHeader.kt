package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionWithHeader(
	modifier: Modifier = Modifier,
	title: String,
	optionName: String,
	isSeeAllVisible: Boolean = true,
	onSeeAll: () -> Unit = {},
	content: (@Composable () -> Unit)?,
) {
	Column(
		modifier = modifier
			.fillMaxWidth(),
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.bodySmall
					.copy(fontWeight = FontWeight.Bold),
			)
			if (isSeeAllVisible) {
				Text(
					modifier = Modifier
						.clip(CircleShape)
						.clickable { onSeeAll() }
						.padding(
							horizontal = 6.dp,
							vertical = 3.dp,
						),
					text = optionName,
					style = MaterialTheme.typography.titleLarge
						.copy(fontWeight = FontWeight.Normal),
					color = MaterialTheme.colorScheme.primary,
				)
			}
		}
		Spacer(modifier = Modifier.height(18.dp))
		content?.invoke()
	}
}
