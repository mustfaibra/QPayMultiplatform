package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.decompose.bottomnavholder.BottomNavComponentImpl
import com.mustfaibra.qpaymultiplatform.utils.get

@Composable
fun QPayBottomBar(
	pages: List<BottomNavComponentImpl.BottomNavConfig>,
	current: BottomNavComponentImpl.BottomNavConfig,
	onNavigate: (index: Int) -> Unit,
) {
	Row(
		modifier = Modifier.fillMaxWidth()
			.background(MaterialTheme.colorScheme.surface)
			.padding(horizontal = 12.dp, vertical = 8.dp)
	) {
		pages.forEach { config ->
			Column(
				modifier = Modifier.weight(1f).clickable(
					interactionSource = MutableInteractionSource(),
					indication = null,
					onClick = {
						if (config != current) {
							onNavigate(pages.indexOf(config))
						}
					},
				),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				CompositionLocalProvider(
					LocalContentColor provides if (current == config) MaterialTheme.colorScheme.primary
					else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
				) {
					Icon(
						modifier = Modifier.size(20.dp),
						painter = config.icon.get(),
						contentDescription = config.title.get(),
						tint = LocalContentColor.current,
					)
					Text(
						text = config.title.get(),
						style = MaterialTheme.typography.titleSmall.copy(
							fontWeight = FontWeight.Medium,
							color = LocalContentColor.current,
						),
						maxLines = 1,
					)
				}
			}
		}
	}
}
