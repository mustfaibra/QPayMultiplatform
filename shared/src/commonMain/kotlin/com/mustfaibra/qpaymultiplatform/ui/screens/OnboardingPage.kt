package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.utils.get
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun OnboardingPage(
	onGetStarted: () -> Unit,
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface)
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f),
			contentAlignment = Alignment.Center,
		) {
			Image(
				painter = painterResource(SharedRes.images.financial_planner),
				contentDescription = null,
				modifier = Modifier.fillMaxSize(0.5f),
				contentScale = ContentScale.Crop,
			)
		}
		Column(
			modifier = Modifier
				.padding(bottom = 48.dp)
				.padding(horizontal = 20.dp)
				.fillMaxWidth(),
			verticalArrangement = Arrangement.spacedBy(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text(
				text = SharedRes.strings.onboarding_title.get(),
				style = MaterialTheme.typography.headlineMedium,
				color = MaterialTheme.colorScheme.onBackground,
				textAlign = TextAlign.Center,
			)
			Text(
				text = SharedRes.strings.onboarding_message.get(),
				style = MaterialTheme.typography.titleLarge
					.copy(
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					),
				textAlign = TextAlign.Center,
			)
			Button(
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.primary,
					contentColor = MaterialTheme.colorScheme.onPrimary,
				),
				onClick = onGetStarted,
				shape = MaterialTheme.shapes.medium,
				contentPadding = PaddingValues(
					horizontal = 24.dp,
					vertical = 12.dp,
				)
			) {
				Text(
					text = SharedRes.strings.get_started.get(),
					style = MaterialTheme.typography.bodyMedium
						.copy(fontWeight = FontWeight.Normal),
				)
			}
		}
	}
}
