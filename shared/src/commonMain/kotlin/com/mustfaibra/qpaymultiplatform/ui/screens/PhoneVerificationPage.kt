package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.ui.composables.CustomButton
import com.mustfaibra.qpaymultiplatform.ui.composables.DigitsInput
import com.mustfaibra.qpaymultiplatform.ui.composables.InteractionBlocker
import com.mustfaibra.qpaymultiplatform.ui.composables.ProcessTimeLine
import com.mustfaibra.qpaymultiplatform.ui.states.VerifyPhoneUIState
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.viewmodels.VerifyPhoneViewModel

@Composable
fun PhoneVerificationPage(
	viewModel: VerifyPhoneViewModel,
	onVerified: () -> Unit
) {
	val uiState by remember { viewModel.uiState }
	val otp by remember { viewModel.otp }
	
	LaunchedEffect(uiState) {
		if (uiState is VerifyPhoneUIState.Verified) {
			onVerified()
		}
	}
	
	InteractionBlocker(
		modifier = Modifier
			.fillMaxSize(),
		blockCondition = uiState is VerifyPhoneUIState.Verifying,
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.background)
				.padding(24.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.weight(1f),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = SharedRes.strings.phone_verification.get(),
					style = MaterialTheme.typography.headlineMedium,
					textAlign = TextAlign.Center,
				)
				Spacer(modifier = Modifier.height(48.dp))
				/**
				 * The user's progress indicator
				 */
				ProcessTimeLine(
					stepsCount = 5,
					currentStep = 2,
				)
			}
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.Top,
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = SharedRes.strings.please_enter_otp_sent.get(),
					style = MaterialTheme.typography.bodyMedium
						.copy(fontWeight = FontWeight.Normal),
				)
				Spacer(modifier = Modifier.height(36.dp))
				DigitsInput(
					value = otp,
					onDigitsChange = { value, isFilled ->
						viewModel.updateOtp(value = value)
						if (isFilled) {
							viewModel.validateOtp(
								otp = otp,
							)
						}
					},
				)
				Spacer(modifier = Modifier.height(16.dp))
				Box(
					modifier = Modifier
						.fillMaxWidth()
				) {
					Text(
						modifier = Modifier
							.clip(CircleShape)
							.clickable {}
							.padding(2.dp),
						text = SharedRes.strings.resend_code.get(),
						style = MaterialTheme.typography.titleLarge
							.copy(fontWeight = FontWeight.Medium),
						color = MaterialTheme.colorScheme.onSurfaceVariant,
					)
				}
				Spacer(modifier = Modifier.height(36.dp))
				CustomButton(
					modifier = Modifier
						.fillMaxWidth(),
					text = SharedRes.strings.verify_code.get(),
					contentColor = MaterialTheme.colorScheme.onPrimary,
					padding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
					textStyle = MaterialTheme.typography.bodyMedium
						.copy(fontWeight = FontWeight.Normal),
					containerColor = MaterialTheme.colorScheme.primary,
					enabled = uiState !is VerifyPhoneUIState.Verifying && otp.length == 4,
					onClick = {
					
					},
					leadingIcon = if (uiState is VerifyPhoneUIState.Verifying) {
						{
							CircularProgressIndicator(
								modifier = Modifier
									.padding(end = 20.dp)
									.size(24.dp),
								color = MaterialTheme.colorScheme.onPrimary,
							)
						}
					}
					else null,
				)
				Spacer(modifier = Modifier.height(16.dp))
				Text(
					modifier = Modifier
						.clip(CircleShape)
						.clickable { },
					text = SharedRes.strings.change_phone_number.get(),
					style = MaterialTheme.typography.titleLarge
						.copy(fontWeight = FontWeight.Medium),
					color = MaterialTheme.colorScheme.onSurfaceVariant,
				)
			}
		}
	}
}
