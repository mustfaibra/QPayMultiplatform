package com.mustfaibra.qpaymultiplatform.viewmodels

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class OnboardingViewModel() : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
}
