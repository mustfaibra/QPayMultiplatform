package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.ActivityFilter
import com.mustfaibra.qpaymultiplatform.data.entity.LocalUser
import com.mustfaibra.qpaymultiplatform.data.entity.User
import com.mustfaibra.qpaymultiplatform.ui.composables.BeneficiariesRowList
import com.mustfaibra.qpaymultiplatform.ui.composables.InteractionBlocker
import com.mustfaibra.qpaymultiplatform.ui.composables.QPayIcon
import com.mustfaibra.qpaymultiplatform.ui.composables.SectionWithHeader
import com.mustfaibra.qpaymultiplatform.ui.composables.TopHeader
import com.mustfaibra.qpaymultiplatform.ui.composables.TransactionLayout
import com.mustfaibra.qpaymultiplatform.ui.states.HomeUiState
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.viewmodels.HomeViewModel
import dev.icerock.moko.resources.ImageResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
	viewModel: HomeViewModel,
	onNavigateToSendMoney: () -> Unit,
) {
	val user = LocalUser.current
		?: throw IllegalStateException("User can't be null!")
	
	LaunchedEffect(user) {
		viewModel.init(me = user)
	}
	
	val scaffoldState = rememberBottomSheetScaffoldState()
	val uiState by remember { viewModel.uiState }
	val filter by remember { viewModel.historyFilter }
	val beneficiaries = viewModel.beneficiaries
	
	BottomSheetScaffold(
		scaffoldState = scaffoldState,
		sheetContent = {
			HomeSheetContent(
				beneficiaries = beneficiaries,
				uiState = uiState,
				pickedFilter = filter,
				onFiltered = {
					viewModel.getFilteredHistory(filter = it)
				},
			)
		},
		sheetSwipeEnabled = true,
		sheetPeekHeight = 300.dp,
		sheetContainerColor = MaterialTheme.colorScheme.background,
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.primary),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			TopHeader(
				name = "${user.fName} ${user.lName}",
				profileImage = LocalUser.current?.profile?.get(),
				onProfileClicked = {
				
				},
				options = {
					// Row of two icons
					Row(
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.spacedBy(6.dp)
					) {
						QPayIcon(
							painter = SharedRes.images.ic_map.get(),
							size = 26.dp,
							padding = PaddingValues(10.dp),
						) {
						
						}
						QPayIcon(
							painter = SharedRes.images.ic_notification.get(),
							size = 40.dp,
							padding = PaddingValues(4.dp),
							tint = MaterialTheme.colorScheme.onPrimary,
						) {
						
						}
					}
				}
			)
			// Available Balance
			Column(
				modifier = Modifier.padding(24.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				Text(
					text = SharedRes.strings.available_balance.get(),
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onPrimary,
				)
				Spacer(modifier = Modifier.padding(4.dp))
				Text(
					text = SharedRes.strings.sar_x.get(user.balance),
					style = MaterialTheme.typography.displaySmall,
					color = MaterialTheme.colorScheme.onPrimary,
				)
			}
			// Shortcuts
			Row(
				modifier = Modifier
					.padding(horizontal = 24.dp)
					.fillMaxWidth()
					.clip(MaterialTheme.shapes.large)
					.background(MaterialTheme.colorScheme.surface)
					.padding(16.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(16.dp)
			) {
				HomeTopShortcut(
					modifier = Modifier.weight(1f),
					icon = SharedRes.images.ic_add,
					text = SharedRes.strings.add_money.get(),
					background = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
					iconTint = MaterialTheme.colorScheme.secondary,
				) {}
				HomeTopShortcut(
					modifier = Modifier.weight(1f),
					icon = SharedRes.images.ic_transfer_money,
					text = SharedRes.strings.send_money.get(),
					background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
					iconTint = MaterialTheme.colorScheme.primary,
				) {
					onNavigateToSendMoney()
				}
				HomeTopShortcut(
					modifier = Modifier.weight(1f),
					icon = SharedRes.images.ic_receive_money,
					text = SharedRes.strings.request_money.get(),
					background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
					iconTint = MaterialTheme.colorScheme.primary,
				) {}
			}
		}
	}
}

@Composable
fun HomeSheetContent(
	beneficiaries: List<User>,
	uiState: HomeUiState,
	pickedFilter: ActivityFilter,
	onFiltered: (ActivityFilter) -> Unit,
) {
	InteractionBlocker(
		blockCondition = uiState is HomeUiState.Filtering,
		shouldShowAlphaEffect = true,
		shouldShowLoadingIndicator = true,
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth(),
			verticalArrangement = Arrangement.spacedBy(12.dp),
		) {
			when (uiState) {
				is HomeUiState.Error -> item {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.fillMaxHeight(0.5f),
						contentAlignment = Alignment.Center,
					) {
						Text(text = "Error with code ${uiState.code} happened!")
					}
				}
				
				is HomeUiState.Loaded, is HomeUiState.Filtering -> {
					item {
						BeneficiariesRowList(
							modifier = Modifier
								.padding(horizontal = 24.dp),
							beneficiaries = beneficiaries,
						)
					}
					item {
						SectionWithHeader(
							modifier = Modifier.padding(
								horizontal = 24.dp,
								vertical = 16.dp
							),
							title = SharedRes.strings.current_activity.get(),
							optionName = SharedRes.strings.see_all.get(),
							content = {
								CurrentActivityFilter(
									currentPickedFilter = pickedFilter,
									onFilterPicked = {
										onFiltered(it)
									},
								)
							},
							onSeeAll = {
							
							},
						)
					}
					when (uiState) {
						is HomeUiState.Filtering -> {
						
						}
						
						is HomeUiState.Loaded -> {
							if (uiState.transactions.isEmpty()) {
								item {
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
							}
							else {
								items(uiState.transactions) { transaction ->
									transaction.otherDude?.let { otherDude ->
										TransactionLayout(
											createdAt = transaction.createdAt,
											isTransferred = transaction.isTransferred,
											value = transaction.value,
											user = otherDude,
											onClicked = {},
										)
									}
								}
							}
						}
						
						else -> {
						
						}
					}
				}
				
				HomeUiState.Loading -> {
					item {
						Box(
							modifier = Modifier
								.fillMaxWidth()
								.fillMaxHeight(0.5f),
							contentAlignment = Alignment.Center,
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
	}
}

@Composable
fun CurrentActivityFilter(
	currentPickedFilter: ActivityFilter? = null,
	onFilterPicked: (ActivityFilter) -> Unit,
) {
	val filters = remember {
		listOf(
			ActivityFilter.LastFiveTrans,
			ActivityFilter.Last3Days,
			ActivityFilter.LastWeek,
			ActivityFilter.LastMonth,
			ActivityFilter.Last3Month,
			ActivityFilter.Last6Month,
			ActivityFilter.Last9Month,
			ActivityFilter.LastYear,
		)
	}
	LazyRow(
		modifier = Modifier
			.padding(top = 4.dp)
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(12.dp),
	) {
		items(filters) { filter ->
			Box(modifier = Modifier
				.border(
					width = Dp.Hairline,
					color = MaterialTheme.colorScheme.primary,
					shape = MaterialTheme.shapes.medium,
				)
				.clip(MaterialTheme.shapes.medium)
				.background(
					if (currentPickedFilter == filter) {
						MaterialTheme.colorScheme.primary
					}
					else Color.Transparent
				)
				.clickable { onFilterPicked(filter) }
				.padding(24.dp, 12.dp)) {
				Text(
					text = filter.title.get(),
					style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
					color = if (currentPickedFilter == filter) {
						MaterialTheme.colorScheme.onPrimary
					}
					else MaterialTheme.colorScheme.primary
				)
			}
		}
	}
}

@Composable
fun HomeTopShortcut(
	modifier: Modifier = Modifier,
	background: Color,
	icon: ImageResource,
	iconTint: Color = contentColorFor(backgroundColor = background),
	text: String,
	onShortcutClicked: () -> Unit,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.clip(CircleShape)
				.background(background)
				.clickable {
					onShortcutClicked()
				}
				.padding(vertical = 4.dp),
			contentAlignment = Alignment.Center,
		) {
			Icon(
				painter = icon.get(),
				contentDescription = null,
				modifier = Modifier
					.clip(CircleShape)
					.padding(12.dp)
					.size(24.dp),
				tint = iconTint,
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		Text(
			text = text,
			style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
		)
	}
}
