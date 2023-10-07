package com.mustfaibra.qpaymultiplatform.ui.states

sealed interface SplashUIState {
	data object Waiting : SplashUIState
	data class Success(val onboardBefore: Boolean) : SplashUIState
}
