package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun VerticalBrandLogo(
	logo: ImageResource,
	text: String,
	textColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Box(
			modifier = Modifier.padding(16.dp).clip(CircleShape)
				.background(MaterialTheme.colorScheme.onPrimary).padding(8.dp),
			contentAlignment = Alignment.Center,
		) {
			Image(
				painter = painterResource(logo),
				contentDescription = null,
				modifier = Modifier.size(48.dp),
			)
		}
		Text(
			text = text,
			style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium),
			color = textColor,
		)
	}
}

@Composable
fun HorizontalBrandLogo(
	logo: ImageResource,
	text: String,
	iconSize: Dp = 40.dp,
	textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
	textColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
	Row(
		modifier = Modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Box(
			modifier = Modifier.clip(CircleShape)
				.background(MaterialTheme.colorScheme.onPrimary)
				.padding(8.dp),
			contentAlignment = Alignment.Center,
		) {
			Image(
				painter = painterResource(logo),
				contentDescription = null,
				modifier = Modifier.size(iconSize),
			)
		}
		Text(
			text = text,
			style = textStyle.copy(fontWeight = FontWeight.Medium),
			color = textColor,
		)
	}
}
