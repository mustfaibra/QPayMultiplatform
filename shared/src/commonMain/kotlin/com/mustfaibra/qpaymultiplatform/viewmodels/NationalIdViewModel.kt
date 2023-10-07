package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.ui.states.NationalIDUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class NationalIdViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	val uiState = mutableStateOf<NationalIDUiState>(NationalIDUiState.Guide)
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
	
	fun updateUiState(value: NationalIDUiState) {
		uiState.value = value
	}
}
