package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun ProcessTimeLine(
	stepsCount: Int,
	currentStep: Int = 0,
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
	) {
		for (step in 1..stepsCount) {
			Column(
				modifier = Modifier.weight(1f)
			) {
				Box(
					modifier = Modifier.fillMaxWidth(),
				) {
					Box(
						modifier = Modifier
							.align(Alignment.Center)
							.fillMaxWidth()
							.height(8.dp)
							.background(
								if (step <= currentStep) {
									MaterialTheme.colorScheme.primary
								}
								else MaterialTheme.colorScheme.surface
							),
					)
					Box(
						modifier = Modifier
							.align(Alignment.CenterEnd)
							.clip(CircleShape)
							.background(
								if (step <= currentStep) {
									MaterialTheme.colorScheme.primary
								}
								else MaterialTheme.colorScheme.surface
							)
							.size(36.dp),
						contentAlignment = Alignment.Center,
					) {
						Text(
							text = "$step",
							style = MaterialTheme.typography.bodySmall
								.copy(fontWeight = FontWeight.Bold),
							color = if (step <= currentStep) {
								MaterialTheme.colorScheme.onPrimary
							}
							else MaterialTheme.colorScheme.primary,
						)
					}
				}
			}
		}
	}
}
