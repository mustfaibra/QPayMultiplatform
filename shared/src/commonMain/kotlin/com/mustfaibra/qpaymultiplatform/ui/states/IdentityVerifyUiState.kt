package com.mustfaibra.qpaymultiplatform.ui.states

sealed interface IdentityVerifyUiState {
	data object Guide : IdentityVerifyUiState
	data object Capture : IdentityVerifyUiState
	class Preview(val uri: String) : IdentityVerifyUiState
	class Failure(val error: String) : IdentityVerifyUiState
	class Verifying(val uri: String) : IdentityVerifyUiState
}
