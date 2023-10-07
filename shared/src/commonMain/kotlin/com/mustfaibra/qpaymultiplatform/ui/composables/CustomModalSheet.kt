package com.mustfaibra.qpaymultiplatform.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalSheet(
	modifier: Modifier = Modifier,
	scaffoldState: BottomSheetScaffoldState,
	sheetContent: @Composable () -> Unit,
	screenContent: @Composable () -> Unit,
	onDismissRequest: () -> Unit,
) {
	BottomSheetScaffold(
		modifier = modifier,
		scaffoldState = scaffoldState,
		sheetContent = { sheetContent() },
		sheetShape = RoundedCornerShape(
			topStartPercent = 10,
			topEndPercent = 10,
		),
		sheetShadowElevation = 32.dp,
		sheetPeekHeight = 0.dp,
		sheetContainerColor = MaterialTheme.colorScheme.surface,
	) {
		Box {
			screenContent()
		}
	}
}
