package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun SecondaryPageTopBar(
	modifier: Modifier = Modifier,
	backgroundColor: Color = MaterialTheme.colorScheme.background,
	contentColor: Color = MaterialTheme.colorScheme.onBackground,
	title: String,
	onBackRequested: () -> Unit,
) {
	Row(
		modifier = modifier.fillMaxWidth().background(backgroundColor),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
	) {
		QPayIcon(
			painter = painterResource(SharedRes.images.google_icon),
			background = Color.Transparent,
			tint = contentColor,
			size = 36.dp,
			padding = PaddingValues(0.dp),
		) {
			onBackRequested()
		}
		Text(
			text = title,
			style = MaterialTheme.typography.bodyMedium,
			color = contentColor,
			textAlign = TextAlign.Center,
		)
		Spacer(modifier = Modifier.width(36.dp))
	}
}
