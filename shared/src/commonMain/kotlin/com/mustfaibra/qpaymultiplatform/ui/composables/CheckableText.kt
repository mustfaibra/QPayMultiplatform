package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckableText(
	modifier: Modifier = Modifier,
	text: String,
	isChecked: Boolean,
	onCheckChange: (checked: Boolean) -> Unit,
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.Top,
		horizontalArrangement = Arrangement.spacedBy(12.dp)
	) {
		CompositionLocalProvider(
			LocalMinimumInteractiveComponentEnforcement provides false
		) {
			Checkbox(checked = isChecked, onCheckedChange = onCheckChange)
		}
		Text(
			text = text,
			style = MaterialTheme.typography.titleMedium,
			textAlign = TextAlign.Justify,
		)
	}
}
