package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.ui.composables.HorizontalBrandLogo
import com.mustfaibra.qpaymultiplatform.ui.composables.SignInWithButton
import com.mustfaibra.qpaymultiplatform.utils.get

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignInOptionsPage(
	onCreateAccount: () -> Unit,
	onSignToQpayAccount: () -> Unit,
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.primary)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			HorizontalBrandLogo(
				logo = SharedRes.images.qpay,
				text = SharedRes.strings.app_name.get()
			)
		}
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 24.dp),
			verticalArrangement = Arrangement.spacedBy(20.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text(
				modifier = Modifier
					.fillMaxWidth(0.6f),
				text = SharedRes.strings.save_in_your_q_pocket.get(),
				style = MaterialTheme.typography.bodyLarge.copy(
					fontWeight = FontWeight.Normal,
				),
				color = MaterialTheme.colorScheme.onPrimary,
				textAlign = TextAlign.Center,
			)
			SignInWithButton(
				modifier = Modifier
					.padding(top = 24.dp)
					.fillMaxWidth(),
				text = SharedRes.strings.sign_in_with_qpay.get(),
				optionIcon = SharedRes.images.qpay,
				textStyle = MaterialTheme.typography.bodyMedium
					.copy(fontWeight = FontWeight.Normal),
				containerColor = MaterialTheme.colorScheme.onSurface,
				contentColor = MaterialTheme.colorScheme.surface,
				iconTint = MaterialTheme.colorScheme.surface,
				onClick = {},
			)
			SignInWithButton(
				modifier = Modifier
					.fillMaxWidth(),
				text = SharedRes.strings.sign_in_with_apple.get(),
				optionIcon = SharedRes.images.apple_icon,
				textStyle = MaterialTheme.typography.bodyMedium
					.copy(fontWeight = FontWeight.Normal),
				containerColor = MaterialTheme.colorScheme.onPrimary,
				contentColor = MaterialTheme.colorScheme.onBackground,
				onClick = onSignToQpayAccount,
			)
			SignInWithButton(
				modifier = Modifier
					.fillMaxWidth(),
				text = SharedRes.strings.sign_in_with_google.get(),
				optionIcon = SharedRes.images.google_icon,
				textStyle = MaterialTheme.typography.bodyMedium
					.copy(fontWeight = FontWeight.Normal),
				containerColor = MaterialTheme.colorScheme.inversePrimary,
				contentColor = MaterialTheme.colorScheme.onPrimary,
				onClick = {},
			)
			Text(
				modifier = Modifier
					.clip(CircleShape)
					.clickable { onCreateAccount() }
					.padding(horizontal = 16.dp, vertical = 4.dp),
				text = SharedRes.strings.create_an_account.get(),
				style = MaterialTheme.typography.titleLarge
					.copy(fontWeight = FontWeight.Medium),
				color = MaterialTheme.colorScheme.onPrimary,
			)
		}
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(24.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text(
				text = SharedRes.strings.by_continue_you_agree_terms.get(),
				style = MaterialTheme.typography.titleMedium
					.copy(fontWeight = FontWeight.Normal),
				color = MaterialTheme.colorScheme.onPrimary,
				textAlign = TextAlign.Justify
			)
			FlowRow(
				verticalArrangement = Arrangement.Center,
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				Text(
					modifier = Modifier
						.clip(CircleShape)
						.clickable { },
					text = SharedRes.strings.terms_and_conditions.get(),
					style = MaterialTheme.typography.titleMedium
						.copy(fontWeight = FontWeight.Bold),
					color = MaterialTheme.colorScheme.onPrimary,
				)
				Text(
					text = " & ",
					style = MaterialTheme.typography.titleMedium
						.copy(fontWeight = FontWeight.Normal),
					color = MaterialTheme.colorScheme.onPrimary,
				)
				Text(
					modifier = Modifier
						.clip(CircleShape)
						.clickable { },
					text = SharedRes.strings.privacy_and_policies.get(),
					style = MaterialTheme.typography.titleMedium
						.copy(fontWeight = FontWeight.Bold),
					color = MaterialTheme.colorScheme.onPrimary,
				)
			}
		}
		
	}
}
