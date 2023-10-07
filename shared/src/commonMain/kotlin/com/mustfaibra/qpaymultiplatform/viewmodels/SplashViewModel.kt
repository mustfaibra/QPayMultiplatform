package com.mustfaibra.qpaymultiplatform.viewmodels

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.ui.states.SplashUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	private val _uiState = MutableStateFlow<SplashUIState>(SplashUIState.Waiting)
	val uiState: StateFlow<SplashUIState> = _uiState
	
	init {
		checkOnboardingState()
	}
	
	private fun checkOnboardingState() {
		viewModelScope.launch {
			delay(3000)
			_uiState.update {
				SplashUIState.Success(
					onboardBefore = false
				)
			}
		}
	}
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
}
