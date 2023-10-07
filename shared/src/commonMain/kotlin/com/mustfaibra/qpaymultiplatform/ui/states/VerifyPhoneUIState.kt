package com.mustfaibra.qpaymultiplatform.ui.states

sealed interface VerifyPhoneUIState {
	data object Idle : VerifyPhoneUIState
	data object Verifying : VerifyPhoneUIState
	data object Verified : VerifyPhoneUIState
	data object Error : VerifyPhoneUIState
}
