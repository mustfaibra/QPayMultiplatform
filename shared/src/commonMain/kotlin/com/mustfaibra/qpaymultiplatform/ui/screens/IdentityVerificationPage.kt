package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.ui.composables.BulletListItem
import com.mustfaibra.qpaymultiplatform.ui.composables.ProcessTimeLine
import com.mustfaibra.qpaymultiplatform.ui.states.IdentityVerifyUiState
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.utils.permissions.Permission
import com.mustfaibra.qpaymultiplatform.utils.permissions.PermissionLauncher
import com.mustfaibra.qpaymultiplatform.viewmodels.IdentityVerificationViewModel
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay

@Composable
fun IdentityVerificationPage(
	viewModel: IdentityVerificationViewModel,
	onIdentityVerified: () -> Unit,
	onStatusBarColorChangeRequest: (color: Color) -> Unit,
) {
	val uiState by remember { viewModel.uiState }
	
	val cameraPermissionLauncher = PermissionLauncher().createLaunchRequest(
		permission = Permission.Camera,
	)
	
	when (uiState) {
		is IdentityVerifyUiState.Guide -> {
			onStatusBarColorChangeRequest(
				MaterialTheme.colorScheme.surface,
			)
			IdentityVerificationGuideView(
				onProceed = {
					cameraPermissionLauncher.launch(
						onGranted = {
							viewModel.updateUiState(
								value = IdentityVerifyUiState.Capture,
							)
						},
						onDenied = {
							viewModel.updateUiState(
								value = IdentityVerifyUiState.Failure("Permission not granted")
							)
						},
					)
				},
			)
		}
		
		is IdentityVerifyUiState.Capture -> {
			onStatusBarColorChangeRequest(
				MaterialTheme.colorScheme.inversePrimary,
			)
			IdentityVerificationCameraView(
				onCaptureSuccess = {
					viewModel.updateUiState(
						value = IdentityVerifyUiState.Preview(uri = it),
					)
				},
				onFailed = {
					viewModel.updateUiState(
						value = IdentityVerifyUiState.Failure(error = it),
					)
				},
			)
		}
		
		is IdentityVerifyUiState.Preview,
		is IdentityVerifyUiState.Verifying -> {
			onStatusBarColorChangeRequest(
				MaterialTheme.colorScheme.surface,
			)
			val uri = if (uiState is IdentityVerifyUiState.Preview)
				(uiState as IdentityVerifyUiState.Preview).uri
			else (uiState as IdentityVerifyUiState.Verifying).uri
			CapturedImagePreview(
				uri = uri,
				isVerifying = uiState is IdentityVerifyUiState.Verifying,
				onConfirmed = {
					viewModel.updateUiState(
						value = IdentityVerifyUiState.Verifying(uri = it)
					)
				},
				onRecapture = {
					viewModel.updateUiState(
						value = IdentityVerifyUiState.Capture,
					)
				},
				onIdentityVerified = {
					onIdentityVerified()
				}
			)
		}
		
		is IdentityVerifyUiState.Failure -> {
			onStatusBarColorChangeRequest(
				MaterialTheme.colorScheme.surface,
			)
			Box(
				modifier = Modifier.fillMaxSize()
					.background(MaterialTheme.colorScheme.surface)
			) {
				Text(
					text = (uiState as IdentityVerifyUiState.Failure).error,
					style = MaterialTheme.typography.bodyMedium,
				)
			}
		}
	}
}

@Composable
fun CapturedImagePreview(
	uri: String,
	isVerifying: Boolean = false,
	onRecapture: () -> Unit = {},
	onConfirmed: (uri: String) -> Unit,
	onIdentityVerified: () -> Unit,
) {
	val progress = remember { mutableStateOf(0f) }
	val scanProgress by animateFloatAsState(
		targetValue = progress.value,
		label = "scan-progress-animation",
		animationSpec = TweenSpec(
			durationMillis = 5000,
			easing = LinearEasing,
		)
	)
	val scanTimer = remember {
		mutableStateOf(5)
	}
	
	LaunchedEffect(key1 = isVerifying) {
		if (isVerifying) {
			progress.value = 1f
			delay(5_000L)
			onIdentityVerified()
		}
	}
	
	Column(
		modifier = Modifier.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Column(
			modifier = Modifier.fillMaxWidth().padding(48.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			val timerString = SharedRes.strings.it_will_take_seconds.get(scanTimer.value)
				.split("|")
			
			
			Text(
				text = if (isVerifying) {
					SharedRes.strings.verifying_your_identity.get()
				}
				else {
					SharedRes.strings.do_you_want_to_proceed_with_this_image.get()
				},
				style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
				textAlign = TextAlign.Center,
			)
			if (isVerifying) {
				Text(
					text = buildAnnotatedString {
						append(timerString[0])
						withStyle(
							MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
								.toSpanStyle(),
						) {
							append("${scanTimer.value}")
						}
						append(timerString[2])
					},
					style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
					textAlign = TextAlign.Center,
				)
			}
			else {
				Text(
					text = SharedRes.strings.ensure_image_is_completely_clear.get(),
					style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
					textAlign = TextAlign.Center,
				)
			}
		}
		Box(
			modifier = Modifier.padding(24.dp),
		) {
			val imageSize = remember { mutableStateOf(IntSize(0, 0)) }
			Image(
				painter = SharedRes.images.fashion_designer.get(),
				contentDescription = null,
				modifier = Modifier
					.align(Alignment.Center)
					.onGloballyPositioned {
						imageSize.value = it.size
					}
					.clip((CircleShape))
					.aspectRatio(1f),
				contentScale = ContentScale.Crop,
			)
			if (isVerifying) {
				val density = LocalDensity.current
				with(density) {
					CircularProgressIndicator(
						modifier = Modifier.size(
							width = imageSize.value.width.toDp(),
							height = imageSize.value.height.toDp(),
						),
						progress = scanProgress,
						color = MaterialTheme.colorScheme.primary,
						trackColor = MaterialTheme.colorScheme.surface,
						strokeWidth = 12.dp,
						strokeCap = StrokeCap.Round,
					)
				}
			}
			else {
				Icon(
					modifier = Modifier
						.align(Alignment.TopStart)
						.border(
							width = 4.dp,
							shape = CircleShape,
							color = MaterialTheme.colorScheme.surface,
						)
						.clip(CircleShape)
						.background(MaterialTheme.colorScheme.error)
						.clickable {
							onRecapture()
						}
						.padding(8.dp)
						.size(36.dp),
					painter = SharedRes.images.ic_retry.get(),
					contentDescription = null,
					tint = MaterialTheme.colorScheme.onError,
				)
				Icon(
					modifier = Modifier
						.align(Alignment.BottomEnd)
						.border(
							width = 4.dp,
							shape = CircleShape,
							color = MaterialTheme.colorScheme.surface,
						)
						.clip(CircleShape)
						.background(Color.Green)
						.clickable {
							onConfirmed(uri)
						}
						.padding(8.dp)
						.size(36.dp),
					painter = SharedRes.images.ic_check.get(),
					contentDescription = null,
					tint = MaterialTheme.colorScheme.onPrimary,
				)
			}
		}
	}
}

@Composable
private fun IdentityVerificationCameraView(
	onCaptureSuccess: (uri: String) -> Unit,
	onFailed: (exception: String) -> Unit,
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.inversePrimary),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceAround,
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth(0.8f)
				.aspectRatio(1f)
				.clip(CircleShape)
				.background(MaterialTheme.colorScheme.onPrimary),
		)
		Icon(
			modifier = Modifier
				.clip(CircleShape)
				.background(MaterialTheme.colorScheme.onPrimary)
				.clickable {
					onCaptureSuccess("")
				}
				.padding(16.dp)
				.size(36.dp),
			painter = painterResource(SharedRes.images.ic_camera),
			contentDescription = null,
			tint = MaterialTheme.colorScheme.primary,
		)
	}
}

@Composable
fun IdentityVerificationGuideView(
	onProceed: () -> Unit = {},
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface)
			.padding(24.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Column(
			modifier = Modifier
				.weight(1f)
				.fillMaxWidth(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Image(
				modifier = Modifier.weight(1f),
				painter = painterResource(SharedRes.images.fashion_designer),
				contentDescription = null,
			)
			Spacer(modifier = Modifier.height(24.dp))
			ProcessTimeLine(
				stepsCount = 5,
				currentStep = 4,
			)
			Spacer(modifier = Modifier.height(24.dp))
			Text(
				text = SharedRes.strings.take_selfie_to_verify_identity.get(),
				style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
				textAlign = TextAlign.Center,
			)
			Spacer(modifier = Modifier.height(16.dp))
			Text(
				text = SharedRes.strings.quick_and_easy_identity_verification.get(),
				style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
				textAlign = TextAlign.Center,
			)
		}
		Column(
			modifier = Modifier.fillMaxWidth().weight(1f),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			val selfiesTips = listOf(
				SharedRes.strings.get_cap_off.get(),
				SharedRes.strings.take_off_glasses.get(),
				SharedRes.strings.find_a_well_lit_area.get(),
				SharedRes.strings.steady_your_camera.get(),
				SharedRes.strings.follow_instructions.get(),
			)
			Spacer(modifier = Modifier.weight(1f))
			Text(
				modifier = Modifier.fillMaxWidth(),
				text = SharedRes.strings.verify_identity_requirements.get(),
				style = MaterialTheme.typography.bodyMedium,
			)
			LazyColumn(
				modifier = Modifier
					.padding(top = 8.dp)
					.fillMaxWidth(),
				verticalArrangement = Arrangement.spacedBy(8.dp),
			) {
				items(selfiesTips) { tip ->
					BulletListItem(text = tip)
				}
			}
			Icon(
				modifier = Modifier
					.padding(top = 24.dp)
					.clip(CircleShape)
					.background(MaterialTheme.colorScheme.primary)
					.clickable {
						onProceed()
					}
					.padding(12.dp)
					.size(30.dp),
				painter = SharedRes.images.ic_camera.get(),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onPrimary,
			)
		}
	}
}
