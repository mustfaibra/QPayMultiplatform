package com.mustfaibra.qpaymultiplatform.viewmodels

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.mustfaibra.qpaymultiplatform.data.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RootViewModel : InstanceKeeper.Instance {
	private val viewModelScope = CoroutineScope(Dispatchers.Unconfined)
	private val _user = MutableStateFlow<User?>(null)
	val user: StateFlow<User?> = _user
	
	fun updateLoggedUser(user: User?) {
		_user.update { user }
	}
	
	override fun onDestroy() {
		viewModelScope.cancel()
	}
	
}
