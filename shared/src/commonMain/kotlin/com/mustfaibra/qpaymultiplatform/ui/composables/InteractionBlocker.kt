package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun InteractionBlocker(
	modifier: Modifier = Modifier,
	blockCondition: Boolean,
	shouldShowLoadingIndicator: Boolean = false,
	shouldShowAlphaEffect: Boolean = false,
	content: @Composable () -> Unit,
) {
	Box(modifier = modifier) {
		content()
		if (blockCondition) {
			Box(
				modifier = Modifier
					.clickable(
						indication = null,
						interactionSource = MutableInteractionSource(),
						onClick = {},
					)
					.fillMaxSize()
					.background(
						if(shouldShowAlphaEffect){
							MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
						} else Color.Transparent
					),
				contentAlignment = Alignment.Center,
			) {
				if (shouldShowLoadingIndicator) {
					CircularProgressIndicator(
						color = MaterialTheme.colorScheme.primary,
					)
				}
			}
		}
	}
	
}
