package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.ui.composables.BulletListItem
import com.mustfaibra.qpaymultiplatform.ui.composables.CheckableText
import com.mustfaibra.qpaymultiplatform.ui.composables.CustomButton
import com.mustfaibra.qpaymultiplatform.ui.composables.CustomModalSheet
import com.mustfaibra.qpaymultiplatform.ui.composables.ProcessTimeLine
import com.mustfaibra.qpaymultiplatform.ui.states.NationalIDUiState
import com.mustfaibra.qpaymultiplatform.ui.states.NationalIdSide
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.utils.permissions.Permission
import com.mustfaibra.qpaymultiplatform.utils.permissions.PermissionLauncher
import com.mustfaibra.qpaymultiplatform.viewmodels.NationalIdViewModel
import kotlinx.coroutines.launch

@Composable
fun NationalIDCapturePage(
	viewModel: NationalIdViewModel,
	onCaptured: (front: String, back: String) -> Unit,
) {
	val uiState by remember { viewModel.uiState }
	val cameraPermissionLauncher = PermissionLauncher().createLaunchRequest(
		permission = Permission.Camera,
	)
	
	when (uiState) {
		is NationalIDUiState.Guide -> {
			ScanGuideView(
				onProceed = {
					cameraPermissionLauncher.launch(
						onGranted = {
							viewModel.updateUiState(
								value = NationalIDUiState.Capture
							)
						},
						onDenied = {
							viewModel.updateUiState(
								value = NationalIDUiState.PermissionDenied,
							)
						}
					)
				},
			)
		}
		
		is NationalIDUiState.PermissionDenied -> {
			Column(
				modifier = Modifier.fillMaxSize(),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center,
			) {
				Text(
					text = SharedRes.strings.camera_permission_required.get(),
					style = MaterialTheme.typography.titleMedium,
					textAlign = TextAlign.Center,
				)
			}
		}
		
		is NationalIDUiState.Capture -> {
			NationalIDScanView(
				onCompleted = { front, back ->
					viewModel.updateUiState(
						value = NationalIDUiState.Extracting(front, back)
					)
				},
				onCancelScanning = {
					viewModel.updateUiState(
						value = NationalIDUiState.Guide
					)
				},
			)
		}
		
		is NationalIDUiState.Extracting -> {
			val front = (uiState as NationalIDUiState.Extracting).front
			val back = (uiState as NationalIDUiState.Extracting).back
			
			ExtractingNationalIDInfoView(
				front = front,
				back = back,
				onFinished = {
					onCaptured(front, back)
				},
			)
		}
	}
}

@Composable
private fun ExtractingNationalIDInfoView(
	front: String,
	back: String, onFinished: () -> Unit
) {
	val progress = remember { mutableStateOf(0f) }
	val animatedProgress by animateFloatAsState(
		targetValue = progress.value, animationSpec = TweenSpec(
			durationMillis = 3000,
			easing = LinearEasing,
		), finishedListener = {
			onFinished()
		}, label = "extract-details-animation"
	)
	Column(
		modifier = Modifier.fillMaxSize()
			.background(MaterialTheme.colorScheme.inversePrimary).padding(24.dp),
	) {
		Box(
			modifier = Modifier.weight(1f).fillMaxWidth(),
			contentAlignment = Alignment.Center,
		) {
			Icon(
				painter = SharedRes.images.ic_scan.get(),
				contentDescription = null,
				modifier = Modifier.padding(24.dp).fillMaxWidth(),
				tint = MaterialTheme.colorScheme.onPrimary,
			)
		}
		Column(
			modifier = Modifier.fillMaxWidth().weight(1f),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text(
				text = SharedRes.strings.id_verification_in_progress.get(),
				style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
				textAlign = TextAlign.Center,
				color = MaterialTheme.colorScheme.onPrimary,
			)
			Text(
				text = SharedRes.strings.it_will_not_take_long.get(),
				style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
				textAlign = TextAlign.Center,
				color = MaterialTheme.colorScheme.onPrimary,
			)
			Spacer(modifier = Modifier.height(16.dp))
			LinearProgressIndicator(
				progress = animatedProgress,
				trackColor = MaterialTheme.colorScheme.onPrimary,
				color = MaterialTheme.colorScheme.primary,
				strokeCap = StrokeCap.Round,
			)
		}
	}
	LaunchedEffect(key1 = Unit) {
		progress.value = 1f
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScanGuideView(
	onProceed: () -> Unit,
) {
	val coroutine = rememberCoroutineScope()
	val expandedSheetTarget = remember { mutableStateOf<String?>(null) }
	val scaffoldSheetState = rememberBottomSheetScaffoldState(
		bottomSheetState = rememberStandardBottomSheetState()
	)
	
	CustomModalSheet(
		scaffoldState = scaffoldSheetState,
		sheetContent = {
			when (expandedSheetTarget.value) {
				"tips" -> ScanIDTips()
				"why_required" -> WhyIDRequiredSheet()
				
				else -> {}
			}
		},
		screenContent = {
			Column(
				modifier = Modifier.fillMaxSize()
					.background(MaterialTheme.colorScheme.background).padding(24.dp),
			) {
//				Column(
//					modifier = Modifier.fillMaxWidth(),
//					verticalArrangement = Arrangement.Center,
//					horizontalAlignment = Alignment.CenterHorizontally,
//				) {
//					// Show some scanning effect
//					val composition by rememberLottieComposition(
//						LottieCompositionSpec.Asset("scan_animation.json"),
//					)
//					val progress by animateLottieCompositionAsState(
//						composition = composition,
//						speed = 4f,
//						reverseOnRepeat = true,
//						iterations = Int.MAX_VALUE,
//					)
//					LottieAnimation(
//						modifier = Modifier.background(Color.Transparent)
//							.fillMaxWidth(0.5f).aspectRatio(1f),
//						composition = composition,
//						progress = { progress },
//						contentScale = ContentScale.FillBounds,
//					)
//				}
				Column(
					modifier = Modifier.weight(1f).fillMaxWidth(),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center,
				) {
					ProcessTimeLine(
						stepsCount = 5,
						currentStep = 3,
					)
					Spacer(modifier = Modifier.height(40.dp))
					Text(
						text = SharedRes.strings.scan_national_id.get(),
						style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
						textAlign = TextAlign.Center,
					)
					Spacer(modifier = Modifier.height(18.dp))
					Text(
						text = SharedRes.strings.confirm_your_identity_with_few_taps.get(),
						style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
						textAlign = TextAlign.Center,
					)
				}
				Column(
					modifier = Modifier.fillMaxWidth(),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Bottom,
				) {
					val isTermsAccepted = remember { mutableStateOf(false) }
					
					CheckableText(
						text = SharedRes.strings.i_agree_on_id_scanning_policies.get(),
						isChecked = isTermsAccepted.value,
						onCheckChange = {
							isTermsAccepted.value = it
						},
					)
					CustomButton(
						modifier = Modifier
							.padding(vertical = 12.dp)
							.fillMaxWidth(),
						text = SharedRes.strings.start_scanning.get(),
						containerColor = MaterialTheme.colorScheme.primary,
						contentColor = MaterialTheme.colorScheme.onPrimary,
						enabled = isTermsAccepted.value,
						padding = PaddingValues(
							horizontal = 24.dp, vertical = 12.dp
						),
						textStyle = MaterialTheme.typography.bodyMedium.copy(
							fontWeight = FontWeight.Normal
						),
						onClick = onProceed,
						leadingIcon = {
							Icon(
								modifier = Modifier
									.padding(end = 24.dp)
									.size(24.dp),
								painter = SharedRes.images.ic_scan.get(),
								contentDescription = null,
								tint = MaterialTheme.colorScheme.onPrimary,
							)
						},
					)
					Row(
						modifier = Modifier.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.SpaceBetween,
					) {
						Text(
							modifier = Modifier.clip(CircleShape).clickable {
								coroutine.launch {
									expandedSheetTarget.value = "tips"
									scaffoldSheetState.bottomSheetState.expand()
								}
							}.padding(horizontal = 12.dp, vertical = 6.dp),
							text = SharedRes.strings.scan_id_tips.get(),
							style = MaterialTheme.typography.titleLarge,
							color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
						)
						Text(
							modifier = Modifier.clip(CircleShape).clickable {
								coroutine.launch {
									expandedSheetTarget.value = "why_required"
									scaffoldSheetState.bottomSheetState.expand()
								}
							}.padding(horizontal = 12.dp, vertical = 6.dp),
							text = SharedRes.strings.why_is_it_required.get(),
							style = MaterialTheme.typography.titleLarge,
							color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
						)
					}
				}
			}
		},
		onDismissRequest = {
			coroutine.launch { scaffoldSheetState.bottomSheetState.hide() }
		},
	)
}

@Composable
private fun ScanIDTips() {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(0.5f)
			.padding(24.dp),
		verticalArrangement = Arrangement.spacedBy(24.dp)
	) {
		val scanningTips = listOf(
			SharedRes.strings.find_a_well_lit_area.get(),
			SharedRes.strings.steady_your_camera.get(),
			SharedRes.strings.position_the_id_properly.get(),
			SharedRes.strings.zoom_in_if_necessary.get(),
			SharedRes.strings.focus_on_the_id.get(),
		)
		Text(
			text = SharedRes.strings.scan_id_tips.get(),
			style = MaterialTheme.typography.bodyLarge,
		)
		LazyColumn(
			modifier = Modifier.fillMaxWidth(),
			verticalArrangement = Arrangement.spacedBy(12.dp),
		) {
			items(scanningTips) { tip ->
				BulletListItem(text = tip)
			}
		}
	}
}

@Composable
private fun WhyIDRequiredSheet() {
	val whyIdRequired = listOf(
		SharedRes.strings.id_reason_verification_of_identity.get(),
		SharedRes.strings.id_reason_compliance_with_regulations.get(),
		SharedRes.strings.id_reason_enhance_security.get(),
		SharedRes.strings.id_reason_prevention_of_fin_crimes.get(),
		SharedRes.strings.id_reason_streamlined_processes.get(),
	)
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.fillMaxHeight(0.5f)
			.padding(24.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		Text(
			text = SharedRes.strings.why_is_it_required.get(),
			style = MaterialTheme.typography.bodyLarge,
			color = MaterialTheme.colorScheme.onSurface,
		)
		Text(
			text = SharedRes.strings.requiring_id_overall_desc.get(),
			style = MaterialTheme.typography.titleLarge,
			lineHeight = 20.sp,
			textAlign = TextAlign.Justify,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
		)
		LazyColumn(
			modifier = Modifier.fillMaxWidth(),
			verticalArrangement = Arrangement.spacedBy(12.dp),
		) {
			items(whyIdRequired) { reason ->
				BulletListItem(text = reason)
			}
		}
		Spacer(modifier = Modifier.height(24.dp))
		
	}
}

@Composable
private fun NationalIDScanView(
	onCompleted: (front: String, back: String) -> Unit,
	onCancelScanning: () -> Unit,
) {
	val currentIdSide = remember {
		mutableStateOf<NationalIdSide>(NationalIdSide.Front)
	}
	val front = remember { mutableStateOf("") }
	val back = remember { mutableStateOf("") }
	
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.primary),
	) {
		// The viewport frame and border on top of the camera
		Column(
			modifier = Modifier
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		) {
			Text(
				modifier = Modifier
					.padding(8.dp),
				text = when (currentIdSide.value) {
					is NationalIdSide.Front -> {
						SharedRes.strings.scan_front_of_id.get()
					}
					
					is NationalIdSide.Back -> {
						SharedRes.strings.scan_back_of_id.get()
					}
				},
				style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
				color = MaterialTheme.colorScheme.onPrimary,
			)
			Spacer(modifier = Modifier.padding(16.dp))
			// The targeted camera view port borders
			BoxWithConstraints(
				modifier = Modifier
					.fillMaxWidth(0.9f)
					.aspectRatio(1.5f)
					.border(
						width = 3.dp,
						color = MaterialTheme.colorScheme.onPrimary,
						shape = MaterialTheme.shapes.medium,
					)
					.padding(8.dp),
			) {
				Box(
					modifier = Modifier
						.fillMaxSize()
						.clip(shape = MaterialTheme.shapes.medium)
						.background(MaterialTheme.colorScheme.surface),
				)
			}
		}
		// The top section which contains close button
		Row(
			modifier = Modifier
				.align(Alignment.TopStart)
				.fillMaxWidth()
				.padding(24.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Icon(
				imageVector = Icons.Rounded.Close,
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onPrimary,
				modifier = Modifier.size(36.dp).clip(CircleShape)
					.clickable { onCancelScanning() },
			)
		}
		Icon(
			painter = SharedRes.images.ic_scan.get(),
			contentDescription = null,
			modifier = Modifier
				.padding(24.dp)
				.align(Alignment.BottomCenter)
				.size(36.dp)
				.clip(CircleShape)
				.clickable {
					when (currentIdSide.value) {
						is NationalIdSide.Front -> {
							currentIdSide.value = NationalIdSide.Back
						}
						
						is NationalIdSide.Back -> {
							onCompleted(front.value, back.value)
						}
					}
				},
			tint = MaterialTheme.colorScheme.onPrimary,
		)
	}
}
