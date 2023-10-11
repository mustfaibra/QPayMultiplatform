package com.mustfaibra.qpaymultiplatform.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.data.repositories.PocketRepository
import com.mustfaibra.qpaymultiplatform.ui.states.PocketUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PocketViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
	
	val uiState = mutableStateOf<PocketUIState>(PocketUIState.Loading)
	
	init {
		getPockets()
	}
	
	private fun getPockets() {
		viewModelScope.launch {
			uiState.value = PocketUIState.Loading
			PocketRepository.getPockets().let {
				uiState.value = PocketUIState.Loaded(it)
			}
		}
	}
}
