package com.mustfaibra.qpaymultiplatform.viewmodels

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class ProfileViewModel: InstanceKeeper.Instance {
	val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	/**
	 * Called at the end of the [InstanceKeeper]'s scope.
	 */
	override fun onDestroy() {
		viewModelScope.cancel()
	}
}
