package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.ui.states.IdentityVerifyUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class IdentityVerificationViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	val uiState = mutableStateOf<IdentityVerifyUiState>(IdentityVerifyUiState.Guide)
	
	fun updateUiState(value: IdentityVerifyUiState) {
		uiState.value = value
	}
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
}
