package com.mustfaibra.qpaymultiplatform.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustfaibra.qpaymultiplatform.SharedRes
import com.mustfaibra.qpaymultiplatform.ui.composables.CustomButton
import com.mustfaibra.qpaymultiplatform.ui.composables.LogoBox
import com.mustfaibra.qpaymultiplatform.ui.composables.QPayIcon
import com.mustfaibra.qpaymultiplatform.utils.get
import com.mustfaibra.qpaymultiplatform.viewmodels.QrPayViewModel


@Composable
fun QrPayPage(
	viewModel: QrPayViewModel,
	onCancelScanningQr: () -> Unit,
) {
	Column(
		modifier = Modifier.fillMaxSize()
			.background(MaterialTheme.colorScheme.inversePrimary).padding(24.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.Start,
		) {
			QPayIcon(
				painter = SharedRes.images.ic_back.get(),
				background = Color.Transparent,
				tint = MaterialTheme.colorScheme.onPrimary,
				size = 36.dp,
				padding = PaddingValues(0.dp),
			) {
				onCancelScanningQr()
			}
		}
		Column(
			modifier = Modifier.fillMaxWidth().weight(1f),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		) {
			Box(
				modifier = Modifier
					.fillMaxWidth(0.8f)
					.aspectRatio(1f)
					.clip(MaterialTheme.shapes.large)
					.background(MaterialTheme.colorScheme.onPrimary),
			) {
				Image(
					painter = SharedRes.images.ic_qr.get(),
					contentDescription = null,
					modifier = Modifier.fillMaxSize()
				)
			}
			Spacer(modifier = Modifier.height(36.dp))
			Text(
				text = SharedRes.strings.scan_the_qr_to_send_the_money.get(),
				style = MaterialTheme.typography.titleLarge,
				color = MaterialTheme.colorScheme.onPrimary,
				textAlign = TextAlign.Center,
			)
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			LogoBox(icon = SharedRes.images.qpay.get(), logoSize = 24.dp)
			CustomButton(
				text = SharedRes.strings.my_q_code.get(),
				textStyle = MaterialTheme.typography.titleLarge,
				trailingIcon = {
					Icon(
						painter = SharedRes.images.ic_qr.get(),
						contentDescription = null,
						modifier = Modifier.padding(start = 12.dp).size(20.dp),
						tint = MaterialTheme.colorScheme.onPrimary,
					)
				},
				shape = MaterialTheme.shapes.small,
				padding = PaddingValues(
					horizontal = 18.dp,
					vertical = 8.dp,
				)
			) {
			
			}
			QPayIcon(
				painter = SharedRes.images.ic_image.get(),
				background = MaterialTheme.colorScheme.onPrimary,
				tint = MaterialTheme.colorScheme.onBackground,
			) {
			
			}
		}
	}
}

//@Composable
//private fun QrView() {
//	val imageCapture = remember { ImageCapture.Builder().build() }
//	val context = LocalContext.current
//	val cameraProvider = remember {
//		mutableStateOf<ProcessCameraProvider?>(null)
//	}
//	LaunchedEffect(key1 = Unit) {
//		cameraProvider.value = context.getCameraProvider()
//	}
//	cameraProvider.value?.let { processCameraProvider ->
//		CameraView(
//			modifier = Modifier
//				.fillMaxWidth(0.8f)
//				.aspectRatio(1f)
//				.clip(MaterialTheme.shapes.medium),
//			imageCapture = imageCapture,
//			cameraProvider = processCameraProvider,
//			lensFacing = CameraSelector.LENS_FACING_BACK,
//		)
//	} ?: run {
//		Text(
//			modifier = Modifier.padding(horizontal = 36.dp),
//			text = SharedRes.strings.please_wait_a_seconds.get(),
//			textAlign = TextAlign.Center,
//		)
//	}
//}
