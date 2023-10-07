package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.utils.get

@Composable
fun TopHeader(
	name: String,
	profileImage: Painter?,
	onProfileClicked: () -> Unit,
	options: (@Composable () -> Unit)?,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.background(MaterialTheme.colorScheme.primary)
			.padding(24.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		// Profile picture
		QPayPicture(
			image = profileImage ?: SharedRes.images.dreadlocked_man.get(),
			onImageClicked = onProfileClicked,
			background = MaterialTheme.colorScheme.onPrimary,
			size = 48.dp,
		)
		
		// Column containing greet and name
		Column(
			modifier = Modifier
				.padding(start = 24.dp)
				.weight(1f),
		) {
			Text(
				text = "Hey, again",
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onPrimary,
			)
			Text(
				text = name,
				style = MaterialTheme.typography.titleLarge,
				color = MaterialTheme.colorScheme.onPrimary,
			)
		}
		options?.invoke()
	}
}
