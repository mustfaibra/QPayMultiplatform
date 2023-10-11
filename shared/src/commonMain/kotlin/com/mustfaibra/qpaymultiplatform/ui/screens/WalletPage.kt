package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.LocalUser
import com.mustfaibra.qpaymultiplatform.ui.composables.AnimatedMoneyCounter
import com.mustfaibra.qpaymultiplatform.ui.composables.QPayIcon
import com.mustfaibra.qpaymultiplatform.ui.composables.QPayOutlineButton
import com.mustfaibra.qpaymultiplatform.ui.composables.SectionWithHeader
import com.mustfaibra.qpaymultiplatform.ui.composables.StackOfCardsSection
import com.mustfaibra.qpaymultiplatform.ui.composables.TransactionLayout
import com.mustfaibra.qpaymultiplatform.ui.states.WalletUiState
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.viewmodels.WalletViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WalletPage(
	viewModel: WalletViewModel,
) {
	val user = LocalUser.current ?: throw IllegalStateException()
	val cards = user.cards
	val uiState by remember { viewModel.uiState }
	
	LaunchedEffect(user) {
		viewModel.getHistory(me = user)
	}
	
	LazyColumn(
		modifier = Modifier
			.background(MaterialTheme.colorScheme.background),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		stickyHeader {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.background(MaterialTheme.colorScheme.background)
					.padding(24.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				Text(
					text = SharedRes.strings.wallet.get(),
					style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
				)
				QPayIcon(
					painter = SharedRes.images.ic_add.get(),
					background = MaterialTheme.colorScheme.primary,
					padding = PaddingValues(6.dp),
					tint = MaterialTheme.colorScheme.onPrimary,
				) {
				
				}
			}
		}
		// Available Balance with options (topUp, withDraw)
		item {
			Column(
				modifier = Modifier.padding(24.dp),
				horizontalAlignment = Alignment.Start,
			) {
				Text(
					text = SharedRes.strings.available_balance.get(),
					style = MaterialTheme.typography.titleLarge,
					color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
				)
				Spacer(modifier = Modifier.padding(4.dp))
				AnimatedMoneyCounter(
					target = user.balance,
					textStyle = MaterialTheme.typography.displaySmall
						.copy(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
				)
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(top = 24.dp),
					verticalAlignment = Alignment.CenterVertically,
				) {
					QPayOutlineButton(
						modifier = Modifier.weight(1f),
						text = SharedRes.strings.top_up.get(),
						textStyle = MaterialTheme.typography.titleLarge,
					) {
					
					}
					Spacer(modifier = Modifier.width(24.dp))
					QPayOutlineButton(
						modifier = Modifier.weight(1f),
						text = SharedRes.strings.withdraw.get(),
						textStyle = MaterialTheme.typography.titleLarge,
					) {
					
					}
				}
			}
		}
		// Cards Section
		item {
			cards.let {
				StackOfCardsSection(
					modifier = Modifier
						.padding(horizontal = 24.dp),
					cards = it,
					cardsOffset = 40f,
				)
			}
		}
		item {
			Spacer(modifier = Modifier.height(40.dp))
			SectionWithHeader(
				modifier = Modifier
					.padding(horizontal = 24.dp),
				title = SharedRes.strings.transactions.get(),
				optionName = SharedRes.strings.see_all.get(),
				content = null,
				onSeeAll = {
				
				},
			)
		}
		item {
			WalletTransactionsSection(uiState = uiState)
		}
	}
}

@Composable
fun WalletTransactionsSection(uiState: WalletUiState) {
	when (uiState) {
		WalletUiState.Loading -> Box(
			modifier = Modifier
				.fillMaxWidth()
				.padding(24.dp),
			contentAlignment = Alignment.Center,
		) {
			CircularProgressIndicator(
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.size(48.dp),
			)
		}
		
		is WalletUiState.Loaded -> {
			Column(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(12.dp),
			) {
				if (uiState.transactions.isEmpty()) {
					Text(
						modifier = Modifier
							.fillMaxWidth()
							.padding(24.dp)
							.alpha(alpha = 0.7f),
						text = SharedRes.strings.no_transactions_in_this_period.get(),
						style = MaterialTheme.typography.titleMedium,
						textAlign = TextAlign.Center,
					)
				}
				else {
					uiState.transactions.forEach { transaction ->
						key(transaction.id) {
							transaction.otherDude?.let {
								TransactionLayout(
									createdAt = transaction.createdAt,
									isTransferred = transaction.isTransferred,
									value = transaction.value,
									user = it,
									onClicked = {},
								)
							}
						}
					}
				}
			}
		}
		
		is WalletUiState.Error -> {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.padding(24.dp),
				contentAlignment = Alignment.Center,
			) {
				Text(text = "Error with code ${uiState.code} happened!")
			}
		}
	}
}
