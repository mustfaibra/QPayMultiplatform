package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.resources.LocalStringProvider
import com.mustfaibra.qpaymultiplatform.ui.composables.BeneficiariesRowList
import com.mustfaibra.qpaymultiplatform.ui.composables.SecondaryPageTopBar
import com.mustfaibra.qpaymultiplatform.ui.composables.SectionWithHeader
import com.mustfaibra.qpaymultiplatform.ui.states.SendMoneyUiState
import com.mustfaibra.qpaymultiplatform.viewmodels.SendMoneyViewModel

@Composable
fun SendMoneyPage(
	onBackRequested: () -> Unit,
) {
	val stringsProvider = LocalStringProvider.current
		?: throw IllegalStateException()
	
	val sendMoneyVM = remember { SendMoneyViewModel() }
	val uiState by remember { sendMoneyVM.uiState }
	
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background)
			.padding(horizontal = 24.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(24.dp),
	) {
		SecondaryPageTopBar(
			title = stringsProvider.get(SharedRes.strings.send_money),
			onBackRequested = onBackRequested,
		)
		when (uiState) {
			is SendMoneyUiState.Error -> {
				// Show some error UI here
			}
			
			is SendMoneyUiState.Loaded -> {
				// The search input
				SendMoneySearchInput(
					holderText = stringsProvider.get(SharedRes.strings.search_a_name),
				)
				// The friends list row
				BeneficiariesRowList(
					modifier = Modifier.fillMaxWidth(),
					beneficiaries = (uiState as SendMoneyUiState.Loaded).beneficiaries,
				)
				// My contacts
				SectionWithHeader(
					modifier = Modifier
						.weight(1f)
						.clip(MaterialTheme.shapes.large)
						.background(MaterialTheme.colorScheme.background),
					title = stringsProvider.get(SharedRes.strings.contacts),
					optionName = stringsProvider.get(SharedRes.strings.see_all),
					content = {
						LazyColumn(
							modifier = Modifier.fillMaxWidth(),
							verticalArrangement = Arrangement.spacedBy(12.dp),
						) {
							items((uiState as SendMoneyUiState.Loaded).contacts) {
								ContactInfoView(
									firstName = it.fName,
									lastName = it.lName,
									contactNum = it.phone ?: "N/A",
								)
							}
						}
					},
					onSeeAll = {
					
					},
				)
			}
			
			SendMoneyUiState.Loading -> {
				Box(
					modifier = Modifier.fillMaxSize(),
					contentAlignment = Alignment.Center
				) {
					CircularProgressIndicator(
						color = MaterialTheme.colorScheme.primary,
						modifier = Modifier.size(48.dp),
					)
				}
			}
		}
	}
}

@Composable
fun ContactInfoView(
	firstName: String,
	lastName: String,
	contactNum: String,
) {
	Row(
		modifier = Modifier
			.padding(bottom = 8.dp)
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		Box(
			modifier = Modifier
				.size(48.dp)
				.clip(CircleShape)
				.background(Color(0xFF208624)),
			contentAlignment = Alignment.Center,
		) {
			Text(
				text = firstName.first().plus(lastName[0].toString()),
				style = MaterialTheme.typography.bodySmall,
				color = Color.White,
			)
		}
		Column(
			modifier = Modifier
				.weight(1f),
		) {
			Text(
				text = "$firstName $lastName",
				style = MaterialTheme.typography.titleSmall
					.copy(fontWeight = FontWeight.Medium),
			)
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = contactNum,
				style = MaterialTheme.typography.titleSmall,
			)
		}
	}
	Divider(color = DividerDefaults.color.copy(alpha = 0.5f))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SendMoneySearchInput(
	holderText: String,
) {
	val keyboardController = LocalSoftwareKeyboardController.current
	val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }
	BasicTextField(
		modifier = Modifier
			.fillMaxWidth(),
		value = searchQuery,
		onValueChange = setSearchQuery,
		textStyle = MaterialTheme.typography.titleMedium
			.copy(fontWeight = FontWeight.Medium),
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Text,
			imeAction = ImeAction.Search
		),
		keyboardActions = KeyboardActions {
			keyboardController?.hide()
		},
		decorationBox = {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.clip(CircleShape)
					.background(MaterialTheme.colorScheme.surface)
					.padding(12.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(10.dp),
			) {
				Icon(
					imageVector = Icons.Rounded.Search,
					contentDescription = null,
					modifier = Modifier
						.size(20.dp),
				)
				Box(
					modifier = Modifier.weight(1f),
				) {
					if (searchQuery.isBlank()) {
						Text(
							modifier = Modifier.fillMaxWidth(),
							text = holderText,
							style = MaterialTheme.typography.titleSmall
								.copy(fontWeight = FontWeight.Medium),
							color = MaterialTheme.colorScheme.onBackground
								.copy(alpha = 0.6f),
						)
					}
					else it()
				}
			}
		}
	)
}
