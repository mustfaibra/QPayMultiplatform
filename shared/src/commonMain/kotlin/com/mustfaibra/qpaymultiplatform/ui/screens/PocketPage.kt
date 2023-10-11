package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.data.entity.LocalUser
import com.mustfaibra.qpaymultiplatform.data.entity.Pocket
import com.mustfaibra.qpaymultiplatform.ui.composables.QPayIcon
import com.mustfaibra.qpaymultiplatform.ui.composables.QPayOutlineButton
import com.mustfaibra.qpaymultiplatform.ui.composables.SectionWithHeader
import com.mustfaibra.qpaymultiplatform.ui.composables.TopHeader
import com.mustfaibra.qpaymultiplatform.ui.states.PocketUIState
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.viewmodels.PocketViewModel
import dev.icerock.moko.resources.compose.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketPage(viewModel: PocketViewModel) {
	val pocketsUiState by remember { viewModel.uiState }
	val scaffoldState = rememberBottomSheetScaffoldState()
	
	BoxWithConstraints(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.primary)
	) {
		BottomSheetScaffold(
			scaffoldState = scaffoldState,
			sheetContent = {
				PocketSheetContent(
					uiState = pocketsUiState,
				)
			},
			sheetSwipeEnabled = true,
			sheetPeekHeight = this.maxHeight.times(0.7f),
			sheetContainerColor = MaterialTheme.colorScheme.background,
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth(),
			) {
				val user = LocalUser.current
				TopHeader(
					name = "${user?.fName} ${user?.lName}",
					profileImage = painterResource(LocalUser.current?.profile ?: SharedRes.images.dreadlocked_man),
					onProfileClicked = {
					
					},
					options = {
						QPayIcon(
							painter = SharedRes.images.ic_edit.get(),
							size = 20.dp,
							tint = MaterialTheme.colorScheme.onPrimary,
						) {
						
						}
					}
				)
				// Available Balance
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.background(MaterialTheme.colorScheme.primary)
						.padding(24.dp),
					horizontalAlignment = Alignment.Start,
				) {
					Text(
						text = SharedRes.strings.sar_x.get(370_000.0),
						style = MaterialTheme.typography.displaySmall,
						color = MaterialTheme.colorScheme.onPrimary,
					)
					Spacer(modifier = Modifier.padding(4.dp))
					Text(
						text = SharedRes.strings.pocket_message_subtitle.get(),
						style = MaterialTheme.typography.labelSmall,
						color = MaterialTheme.colorScheme.onPrimary,
						lineHeight = 18.sp,
					)
				}
			}
		}
	}
}

@Composable
fun PocketSheetContent(
	uiState: PocketUIState,
) {
	when (uiState) {
		PocketUIState.Loading -> {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center,
			) {
				CircularProgressIndicator(
					color = MaterialTheme.colorScheme.primary,
				)
			}
		}
		
		is PocketUIState.Loaded -> {
			LazyColumn(
				modifier = Modifier.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(12.dp),
				contentPadding = PaddingValues(bottom = 24.dp),
			) {
				item {
					SectionWithHeader(
						modifier = Modifier.padding(
							horizontal = 24.dp,
							vertical = 16.dp
						),
						title = SharedRes.strings.pocket.get(),
						optionName = SharedRes.strings.see_all.get(),
						content = {
							Row(
								modifier = Modifier
									.fillMaxWidth()
									.clip(MaterialTheme.shapes.medium)
									.background(MaterialTheme.colorScheme.surface)
									.padding(horizontal = 12.dp, vertical = 8.dp),
								verticalAlignment = Alignment.CenterVertically,
								horizontalArrangement = Arrangement.SpaceAround,
							) {
								// Pocket Saved
								Column(
									modifier = Modifier
										.weight(1f)
										.padding(6.dp)
										.clip(MaterialTheme.shapes.medium)
										.background(
											color = MaterialTheme.colorScheme.primary
												.copy(alpha = 0.1f),
										)
										.padding(14.dp),
								) {
									Text(
										text = SharedRes.strings.pocket_saved.get(),
										style = MaterialTheme.typography.titleSmall
											.copy(
												fontWeight = FontWeight.Medium,
											),
										color = MaterialTheme.colorScheme.onBackground
											.copy(alpha = 0.6f),
									)
									Spacer(modifier = Modifier.height(4.dp))
									Text(
										SharedRes.strings.sar_x.get(788.95),
										style = MaterialTheme.typography.titleLarge,
										color = MaterialTheme.colorScheme.primary,
									)
								}
								// Pocket Expenses
								Column(
									modifier = Modifier
										.weight(1f)
										.padding(6.dp)
										.clip(MaterialTheme.shapes.medium)
										.background(
											color = MaterialTheme.colorScheme.secondary
												.copy(alpha = 0.1f),
										)
										.padding(14.dp),
								) {
									Text(
										text = SharedRes.strings.pocket_expenses.get(),
										style = MaterialTheme.typography.titleSmall
											.copy(
												fontWeight = FontWeight.Medium,
											),
										color = MaterialTheme.colorScheme.onBackground
											.copy(alpha = 0.6f),
									)
									Spacer(modifier = Modifier.height(4.dp))
									Text(
										text = SharedRes.strings.sar_x.get(244.5),
										style = MaterialTheme.typography.titleLarge,
										color = MaterialTheme.colorScheme.secondary,
									)
								}
							}
						},
						onSeeAll = {
						
						},
					)
				}
				items(uiState.pockets) {
					PocketItemUI(
						pocket = it,
						onClicked = {},
					)
				}
				item {
					Row(
						modifier = Modifier
							.padding(
								horizontal = 24.dp,
								vertical = 16.dp
							)
							.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.spacedBy(20.dp),
					) {
						QPayOutlineButton(
							modifier = Modifier.weight(1f),
							text = SharedRes.strings.view_reports.get(),
						) {
						
						}
						QPayOutlineButton(
							modifier = Modifier.weight(1f),
							text = SharedRes.strings.new_pocket.get(),
							icon = SharedRes.images.ic_add.get(),
						) {
						
						}
					}
				}
			}
		}
		
		is PocketUIState.Error -> {
		
		}
	}
}

@Composable
fun PocketItemUI(pocket: Pocket, onClicked: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable {
				onClicked()
			}
			.padding(horizontal = 24.dp, vertical = 8.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(10.dp),
	) {
		Icon(
			painter = painterResource(pocket.icon),
			contentDescription = null,
			modifier = Modifier
				.clip(CircleShape)
				.background(pocket.color.copy(alpha = 0.5f))
				.padding(PaddingValues(8.dp))
				.size(24.dp),
			tint = Color.Unspecified,
		)
		Column(
			modifier = Modifier
				.weight(1f),
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				Column(
					modifier = Modifier.weight(1f),
				) {
					Text(
						text = pocket.name,
						style = MaterialTheme.typography.titleLarge
							.copy(fontWeight = FontWeight.SemiBold),
					)
					Spacer(modifier = Modifier.height(6.dp))
					Text(
						text = pocket.updatedAt,
						style = MaterialTheme.typography.labelSmall,
					)
				}
				Text(
					text = SharedRes.strings.sar_x.get(pocket.goalToReach.toInt()),
					style = MaterialTheme.typography.titleLarge
						.copy(fontWeight = FontWeight.SemiBold),
				)
			}
			Spacer(modifier = Modifier.height(8.dp))
			LinearProgressIndicator(
				modifier = Modifier.fillMaxWidth(),
				trackColor = pocket.color.copy(alpha = 0.2f),
				color = pocket.color,
				progress = pocket.currentSaving.div(pocket.goalToReach).toFloat(),
				strokeCap = StrokeCap.Round,
			)
		}
	}
}
