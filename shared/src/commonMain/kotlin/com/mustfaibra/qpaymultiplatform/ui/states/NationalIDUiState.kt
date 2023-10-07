package com.mustfaibra.qpaymultiplatform.ui.states

sealed interface NationalIDUiState {
	data object Guide : NationalIDUiState
	data object PermissionDenied : NationalIDUiState
	data object Capture : NationalIDUiState
	class Extracting(val front: String, val back: String) : NationalIDUiState
}
