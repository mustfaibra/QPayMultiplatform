package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.ui.composables.DigitsInput
import com.mustfaibra.qpaymultiplatform.ui.composables.HorizontalBrandLogo
import com.mustfaibra.qpaymultiplatform.ui.composables.InteractionBlocker
import com.mustfaibra.qpaymultiplatform.ui.composables.ProcessTimeLine
import com.mustfaibra.qpaymultiplatform.ui.states.CreateAuthUIState
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.viewmodels.CreateAuthenticateViewModel

@Composable
fun CreateAuthenticationPage(
	viewModel: CreateAuthenticateViewModel,
	onAuthenticationCreated: (User) -> Unit,
) {
	val pin by remember { viewModel.pin }
	val uiState by remember { viewModel.uiState }
	
	InteractionBlocker(
		modifier = Modifier.fillMaxSize(),
		blockCondition = uiState is CreateAuthUIState.Creating,
		shouldShowLoadingIndicator = true,
	) {
		Column(
			modifier = Modifier.fillMaxSize()
				.background(MaterialTheme.colorScheme.background).padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceEvenly,
		) {
			HorizontalBrandLogo(
				logo = SharedRes.images.qpay,
				text = SharedRes.strings.app_name.get(),
				textColor = MaterialTheme.colorScheme.primary,
			)
			Column(
				modifier = Modifier.fillMaxWidth(),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(20.dp),
			) {
				ProcessTimeLine(
					stepsCount = 5,
					currentStep = 5,
				)
				Spacer(modifier = Modifier.height(20.dp))
				Text(
					text = SharedRes.strings.complete_account_registration.get(),
					style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
					textAlign = TextAlign.Center,
				)
				Text(
					text = SharedRes.strings.enter_your_pin.get(),
					style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
					textAlign = TextAlign.Center,
				)
				DigitsInput(
					value = pin,
					digitsHorizontalArrangement = Arrangement.Center,
					onDigitsChange = { newValue, isFilled ->
						viewModel.updatePin(newPin = newValue)
						if (isFilled) {
							viewModel.saveUserPin(pin = pin) {
								onAuthenticationCreated(it)
							}
						}
					},
				)
			}
		}
	}
}
